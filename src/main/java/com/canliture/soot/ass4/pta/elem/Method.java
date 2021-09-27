package com.canliture.soot.ass4.pta.elem;

import com.canliture.soot.ass4.pta.analysis.ci.PointerFlowGraph;
import com.canliture.soot.ass4.pta.stmt.*;
import soot.Local;
import soot.SootMethod;
import soot.Unit;
import soot.Value;
import soot.jimple.*;
import soot.tagkit.LineNumberTag;
import soot.tagkit.Tag;

import java.util.*;

/**
 * Created by liture on 2021/9/20 11:07 下午
 *
 * 方法
 */
public class Method {

    private final SootMethod delegate;

    private Set<Statement> pointerAffectingStmt;

    public Method(SootMethod sootMethod) {
        this.delegate = sootMethod;
        initialize();
    }

    /**
     * Translate SootMethod to ours 'pointer-affecting' IR
     *
     * 'pointer-affecting' statements:
     * 1. New      x = new T()
     * 2. Assign   x = y
     * 3. Store    x.f = y
     * 4. Load     y = x.f
     * 5. Call     r = x.k(arg, ...)
     *
     * Java语言中的指针有：
     * 1. Local Variable: x          <- 考虑
     * 2. Static field: C.f           <- 在本作业中我们不考虑(一般实现的话处理成全局变量)
     * 3. instance field: x.f         <- 考虑
     * 4. Array element: array[i]    <- 在本作业中我们不考虑(一般实现的话处理成array[i]抽象成array.index_field)
     */
    private void initialize() {
        pointerAffectingStmt = new LinkedHashSet<>();

        // 避免重复生成对象
        Map<Local, Variable> localMap = new HashMap<>();

        for (Unit unit : delegate.retrieveActiveBody().getUnits()) {
            Stmt stmt = (Stmt) unit;
            if (stmt instanceof AssignStmt) {
                AssignStmt assignStmt = (AssignStmt) stmt;
                Value l = assignStmt.getLeftOp();
                Value r = assignStmt.getRightOp();
                // x = new T
                if (l instanceof Local && r instanceof NewExpr) {
                    Variable x = getVariable(localMap, (Local) l);
                    Allocation alloc = new Allocation(x, assignStmt);
                    addPointerAffectingStmt(stmt, alloc);
                }
                // x = y
                if (l instanceof Local && r instanceof Local) {
                    Variable x = getVariable(localMap, (Local) l);
                    Variable y = getVariable(localMap, (Local) r);
                    Assign assign = new Assign(y, x);
                    addPointerAffectingStmt(stmt, assign);
                }
                // y = x.f
                if (l instanceof Local && r instanceof InstanceFieldRef) {
                    Variable y = getVariable(localMap, (Local) l);
                    Variable x = getVariable(localMap, (Local) ((InstanceFieldRef) r).getBase());
                    Field f = new Field((InstanceFieldRef) r);
                    InstanceLoad load = new InstanceLoad(y, x, f);
                    x.getLoads().add(load);
                    addPointerAffectingStmt(stmt, load);
                }
                // x.f = y
                if (l instanceof InstanceFieldRef && r instanceof Local) {
                    Variable x = getVariable(localMap, (Local) ((InstanceFieldRef) l).getBase());
                    Field f = new Field((InstanceFieldRef) l);
                    Variable y = getVariable(localMap, (Local) r);
                    InstanceStore store = new InstanceStore(x, f, y);
                    x.getStores().add(store);
                    addPointerAffectingStmt(stmt, store);
                }
            }
            if (stmt.containsInvokeExpr()) {
                CallSite callSite = null;
                Variable x = null;

                InvokeExpr invokeExpr = stmt.getInvokeExpr();
                if (invokeExpr instanceof InstanceInvokeExpr) {
                    x = getVariable(localMap, (Local) ((InstanceInvokeExpr) invokeExpr).getBase());
                    if (stmt instanceof AssignStmt && ((AssignStmt) stmt).getLeftOp() instanceof Local) {
                        // r = x.k(arg, ...)
                        Variable r = getVariable(localMap, (Local) ((AssignStmt) stmt).getLeftOp());
                        callSite = new CallSite(stmt, x, r);
                    } else {
                        // x.k(arg, ...)
                        callSite = new CallSite(stmt, x);
                    }
                } else if (invokeExpr instanceof StaticInvokeExpr) {
                    // ClassName.k(arg, ...)   : static call
                    callSite = new CallSite(stmt);
                }

                Call call = new Call(callSite);
                if (x != null) {
                    x.getCalls().add(call);
                }
                addPointerAffectingStmt(stmt, call);
            }
        }
    }

    private void addPointerAffectingStmt(Stmt stmt, Statement ir) {
        ir.setEnclosingMethod(this);
        Tag lineTag = stmt.getTag(LineNumberTag.IDENTIFIER);
        if (lineTag != null) {
            int line = Integer.parseInt(lineTag.toString());
            ir.setLine(line);
        }
        pointerAffectingStmt.add(ir);
    }

    private Variable getVariable(Map<Local, Variable> localMap, Local local) {
        return localMap.computeIfAbsent(local, k -> new Variable(local, this));
    }

    /**
     * 获取方法内的所有指针相关操作(Pointer-affecting)的语句
     * @return
     */
    public Set<Statement> getStatements() {
        return pointerAffectingStmt;
    }

    public SootMethod getSootMethod() {
        return delegate;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Method method = (Method) o;
        return Objects.equals(delegate, method.delegate);
    }

    @Override
    public int hashCode() {
        return Objects.hash(delegate);
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append(delegate.getSignature()).append(" {\n");
        for (Statement stmt : pointerAffectingStmt) {
            buff.append("\t").append(stmt).append("\n");
        }
        buff.append("}");
        return buff.toString();
    }
}
