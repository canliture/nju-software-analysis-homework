package com.canliture.soot.ass5.pta.analysis.cs;

import com.canliture.soot.ass5.pta.analysis.HeapModel;
import com.canliture.soot.ass5.pta.analysis.cg.CallKind;
import com.canliture.soot.ass5.pta.analysis.cg.JimpleCallGraph;
import com.canliture.soot.ass5.pta.analysis.context.Context;
import com.canliture.soot.ass5.pta.analysis.context.ContextSelector;
import com.canliture.soot.ass5.pta.analysis.context.DefaultContext;
import com.canliture.soot.ass5.pta.analysis.data.CSCallSite;
import com.canliture.soot.ass5.pta.analysis.data.CSMethod;
import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.canliture.soot.ass5.pta.analysis.data.CSVariable;
import com.canliture.soot.ass5.pta.elem.CallSite;
import com.canliture.soot.ass5.pta.elem.Method;
import com.canliture.soot.ass5.pta.elem.Obj;
import com.canliture.soot.ass5.pta.elem.Variable;
import com.canliture.soot.ass5.pta.stmt.*;
import soot.*;
import soot.jimple.AssignStmt;
import soot.jimple.InvokeExpr;
import soot.jimple.NewExpr;
import soot.jimple.StaticInvokeExpr;
import soot.toolkits.scalar.Pair;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 11:11 下午
 *
 * 本类实现了指针分析算法(对应Lecture 12的95页)
 */
public class PointerAnalysis {

    /**
     * 堆模型
     */
    protected HeapModel heapModel;

    protected SootMethod sootMethod;
    protected Method entry;

    protected WorkList WL;

    protected PointerFlowGraph PFG;

    protected Set<Statement> S;

    protected Set<CSMethod> RM;

    protected JimpleCallGraph CG;

    protected ContextSelector selector;

    public PointerAnalysis(SootMethod entry, ContextSelector selector) {
        this.sootMethod = entry;
        this.heapModel = HeapModel.v();
        this.selector = selector;
    }

    /**
     * 开始指针分析算法
     */
    public void solve() {
        initialize();
        analysis();
    }

    /**
     * 实现指针分析算法(Lecture 10, Page 115)的前两行;
     * 初始化各种数据结构，entry methods
     */
    protected void initialize() {
        WL = new WorkList();
        PFG = new PointerFlowGraph();
        S = new LinkedHashSet<>();
        RM = new LinkedHashSet<>();
        CG = new JimpleCallGraph();
        entry = new Method(sootMethod);

        CSMethod csMethod = new CSMethod(DefaultContext.v(), entry);
        addReachable(csMethod);
    }

    /**
     * 实现指针分析中的 WorkList 处理主循环
     */
    private void analysis() {
        while (!WL.isEmpty()) {
            Pair<Pointer, PointsToSet> entry = WL.remove();

            // propagate
            Pointer n = entry.getO1();
            PointsToSet pts = entry.getO2();
            PointsToSet delta = propagate(n, pts);

            if (!(n instanceof Var)) {
                continue;
            }

            // now, n represents a variable x
            Var x = (Var) n;

            // foreach x.f = y
            processInstanceStore(x, delta);

            // foreach y = x.f
            processInstanceLoad(x, delta);

            // ProcessCall(x, o_i)
            processCall(x, delta);
        }
    }

    protected void addReachable(CSMethod m) {
        if (!RM.contains(m)) {
            RM.add(m);

            // S U= S_m
            Set<Statement> S_m = m.getMethod().getStatements();
            S.addAll(S_m);

            // foreach i: x = new T() \in S_m
            processAllocations(m);

            // foreach x = y \in S_m
            processLocalAssign(m);

            // foreach l: r = ClassName.k(a1, ..., an)
            processStaticCall(m);
        }
    }

    /**
     * 这个方法实现集合的差运算: delta = pts - pts(n)
     * 另外还实现了Lecture 9, Page 43的 propagate(p, pts) 函数
     *
     * 这里合并了两个步骤到一个方法里面，用于降低冗余的计算
     * @param n
     * @param pts
     * @return pts - pts(n)
     */
    protected PointsToSet propagate(Pointer n, PointsToSet pts) {
        PointsToSet ptsOfn = n.getPointsToSet();
        PointsToSet delta = PointsToSet.difference(pts, ptsOfn);
        if (!delta.isEmpty()) {
            ptsOfn.union(delta);
            // foreach n -> s \in PFG
            for (Pointer s : PFG.getSuccessorOf(n)) {
                WL.addPointerEntry(s, delta);
            }
        }
        return delta;
    }

    /**
     * Lecture 9, Page 43 的 AddEdge 函数
     * @param s PFG边的source
     * @param t PFG边的destination
     */
    protected void addPFGEdge(Pointer s, Pointer t) {
        boolean add = PFG.addEdge(s, t);
        if (add) {
            WL.addPointerEntry(t, s.getPointsToSet());
        }
    }

    /**
     * 处理新的可达方法内的allocations；
     * Lecture 10, Page 118 的 AddReachable函数的第一个foreach循环
     *
     * 提示：
     * 需要实现堆模型(heap abstraction)来获取抽象对象(abstract object).
     * 我们使用allocation-size abstraction，以至于每个allocation site生成一个abstract object
     *
     * assignment4说:
     * 我们能够使用当前类的heapModel字段来获取堆模型上的抽象对象:
     *
     * Allocation alloc = ...;
     * Object allocSite = alloc.getAllocationSite();
     * Obj obj = heapModel.getObj(allocSite, alloc.getType(), method);
     *
     * 这个heapModel尝试自己实现吧...
     * @param m
     */
    protected void processAllocations(CSMethod m) {
        Context c = m.getContext();

        Set<Statement> S_m = m.getMethod().getStatements();
        S_m.stream()
            .filter(s -> s instanceof Allocation)
            .forEach(s -> {
                // i: x = new T()
                Allocation i = (Allocation) s;
                // o_i
                Obj o = heapModel.getObj(i.getAllocationSite(), i.getType(), m.getMethod());
                // <c:x, c:o_i>
                CSVariable csVariable = new CSVariable(c, i.getVar());
                CSObj csObj = new CSObj(c, o);
                WL.addPointerEntry(PFG.getVar(csVariable), PointsToSet.singleton(csObj));
            });
    }

    /**
     * 处理局部变量的赋值；
     * 如: x = y
     *
     * Lecture 10, Page 118 的 AddReachable函数的第二个foreach循环
     * @param m
     */
    protected void processLocalAssign(CSMethod m) {
        Context c = m.getContext();

        Set<Statement> S_m = m.getMethod().getStatements();
        S_m.stream()
            .filter(s -> s instanceof Assign)
            .forEach(s -> {
                // x = y
                Assign assign = (Assign) s;
                // c:y -> c:x
                CSVariable from = new CSVariable(c, assign.getFrom());
                CSVariable to = new CSVariable(c, assign.getTo());
                addPFGEdge(PFG.getVar(from), PFG.getVar(to));
            });
    }

    /**
     * 处理静态函数调用
     * @param m
     */
    protected void processStaticCall(CSMethod m) {
        Method curMethod = m.getMethod();
        Context c = m.getContext();

        Set<Statement> S_m = m.getMethod().getStatements();
        S_m.stream()
           .filter(s -> (s instanceof Call)
                    && CallKind.STATIC == CallKind.getCallKind(((Call) s).getCallSite().getCallSite()))
           .forEach(s -> {
               // l: r = ClassName.k(a1, ..., an)
               Call call = (Call) s;
               CallSite callSite = call.getCallSite();
               StaticInvokeExpr staticCall = (StaticInvokeExpr) callSite.getCallSite().getInvokeExpr();

               // 被调用函数 (不需要Dispatch, 静态可知)
               SootMethod calleeSootMethod = staticCall.getMethod();
               Method calleeMethod = getMethod(calleeSootMethod);

               // Select(c, callSite, calleeMethod)
               CSCallSite csCallSite = new CSCallSite(c, callSite);
               Context ct = selector.selectContext(csCallSite, calleeMethod);

               processCallLink(curMethod, c, calleeMethod, callSite, csCallSite, ct);
           });
    }

    /**
     * 处理调用时的连边
     * @param curMethod 当前函数
     * @param c 当前上下文
     * @param calleeMethod 被调用函数
     * @param callSite 调用点
     * @param csCallSite 上下文敏感的调用点
     * @param ct 被调用函数被选择后的上下文
     */
    private void processCallLink(Method curMethod, Context c, Method calleeMethod, CallSite callSite, CSCallSite csCallSite, Context ct) {
        CSMethod csCallee = new CSMethod(ct, calleeMethod);
        if (!CG.contains(csCallSite, csCallee)) {
            // add c:l -> ct:m to CG
            CG.addEdge(csCallSite,
                       csCallee,
                       CallKind.getCallKind(callSite.getCallSite()));

            addReachable(csCallee);

            // foreach parameter p_i of m do
            //   AddEdge(c:a_i, ct:p_i)
            InvokeExpr invoke = callSite.getCallSite().getInvokeExpr();
            for (int i = 0; i < calleeMethod.getParams().size(); i++) {
                Local arg = (Local) invoke.getArg(i);
                Variable argVariable = curMethod.getVariable(arg);
                CSVariable cs_a_i = new CSVariable(c, argVariable);

                Variable paramVariable = calleeMethod.getParams().get(i);
                CSVariable ct_p_i = new CSVariable(ct, paramVariable);

                addPFGEdge(PFG.getVar(cs_a_i), PFG.getVar(ct_p_i));
            }

            // AddEdge(ct:m_ret, c:r)
            Variable callerRetVar = callSite.getRet();
            CSVariable c_r = new CSVariable(c, callerRetVar);
            if (callerRetVar != null) {
                List<Variable> calleeRetVariableList = calleeMethod.getRetVariable();
                for (Variable calleeRetVar : calleeRetVariableList) {
                    CSVariable ct_m_ret = new CSVariable(ct, calleeRetVar);
                    addPFGEdge(PFG.getVar(ct_m_ret), PFG.getVar(c_r));
                }
            }
        }
    }

    /**
     * 处理字段的写语句;
     * 如: x.f = y
     *
     * Lecture 10, Page 124 Solve函数中处理store语句的foreach循环
     * @param var pts改变的指针节点, 对应的指针变量为x
     * @param pts 改变的部分delta
     */
    protected void processInstanceStore(Var var, PointsToSet pts) {
        Context c = var.getVariable().getContext();

        // c':o_i
        for (CSObj o_i : pts) {

            Set<InstanceStore> stores = var.getVariable().getVariable().getStores();
            // x.f = y
            for (InstanceStore store : stores) {
                // c:y -> c':o_i.f
                CSVariable from = new CSVariable(c, store.getFrom());
                addPFGEdge(PFG.getVar(from), PFG.getInstanceField(o_i, store.getField()));
            }
        }
    }

    /**
     * 处理字段读语句;
     * 如: y = x.f
     *
     * Lecture 10, Page 124 Solve函数中处理load语句的foreach循环
     * @param var pts改变的指针节点, 对应的指针变量为x
     * @param pts 改变的部分delta
     */
    protected void processInstanceLoad(Var var, PointsToSet pts) {
        Context c = var.getVariable().getContext();

        // c'o_i
        for (CSObj o_i : pts) {
            Set<InstanceLoad> loads = var.getVariable().getVariable().getLoads();
            // y = x.f
            for (InstanceLoad load : loads) {
                // c':o_i.f -> c:y
                CSVariable from = new CSVariable(c, load.getTo());
                addPFGEdge(PFG.getInstanceField(o_i, load.getField()), PFG.getVar(from));
            }
        }
    }

    /**
     * 处理函数调用，型如：l: r = var.k(a1, ..., an)
     * @param var
     * @param pts
     */
    protected void processCall(Var var, PointsToSet pts) {
        Method curMethod = var.getVariable().getVariable().getMethod();
        Context c = var.getVariable().getContext();

        // c':o_i
        for (CSObj o_i : pts) {
            Set<Call> calls = var.getVariable().getVariable().getCalls();
            for (Call call : calls) {
                // r = var.k(a1, ..., an)
                CallSite callSite = call.getCallSite();
                // m = Dispatch(o_i, k)
                Method m = dispatch(o_i.getObject(), callSite);

                // Select(c, callSite, c':o_i, m)
                CSCallSite csCallSite = new CSCallSite(c, callSite);
                Context ct = selector.selectContext(csCallSite, o_i, m);

                // add <ct:m_this, c':o_i> to WL
                CSVariable csThis = new CSVariable(ct, m.getThisVariable());
                WL.addPointerEntry(PFG.getVar(csThis), PointsToSet.singleton(o_i));

                // 传播参数/返回值
                processCallLink(curMethod, c, m, callSite, csCallSite, ct);
            }
        }
    }

    protected Method dispatch(Obj o, CallSite callSite) {
        AssignStmt assignStmt = (AssignStmt) o.getAllocSite();
        NewExpr newExpr = (NewExpr) assignStmt.getRightOp();
        RefType refType = (RefType) newExpr.getType();

        SootClass sootClass = refType.getSootClass();

        InvokeExpr invokeExpr = callSite.getCallSite().getInvokeExpr();
        SootMethod sootMethod = invokeExpr.getMethod();

        SootMethod dispatch = dispatch(sootClass, sootMethod);

        Method method = getMethod(dispatch);
        return method;
    }

    /**
     * 从 SootMethod 中获取 Method
     * @param sootMethod
     * @return
     */
    protected Method getMethod(SootMethod sootMethod) {
        Method result = null;
        for (CSMethod m : RM) {
            if (m.getMethod().getSootMethod() == sootMethod) {
                result = m.getMethod();
                break;
            }
        }
        if (result == null) {
            result = new Method(sootMethod);
        }
        return result;
    }

    /**
     * @see com.canliture.soot.ass3.CHACallGraphBuilder#dispatch
     */
    private SootMethod dispatch(SootClass sootClass, SootMethod method) {
        for (SootMethod m : sootClass.getMethods()) {
            if (!m.isAbstract()) {
                // fixme 这里判断方法签名匹配有点粗糙
                // fixme 应该是找Type-Compatible的
                if (m.getName().equals(method.getName())
                        && m.getParameterCount() == method.getParameterCount()) {
                    // 没有参数列表，那么直接匹配到了
                    if (m.getParameterCount() == 0) {
                        return m;
                    }
                    // 否则对比参数列表
                    for (int i = 0; i < m.getParameterCount(); i++) {
                        Type t = m.getParameterType(i);
                        Type t1 = method.getParameterType(i);
                        if (t.toQuotedString().equals(t1.toQuotedString())) {
                            return m;
                        }
                    }
                }
            }
        }
        SootClass superClass = sootClass.getSuperclassUnsafe();
        if (superClass != null) {
            return dispatch(superClass, method);
        }
        return null;
    }

}
