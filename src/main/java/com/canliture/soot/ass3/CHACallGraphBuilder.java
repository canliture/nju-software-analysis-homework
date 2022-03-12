package com.canliture.soot.ass3;

import soot.*;
import soot.jimple.InvokeExpr;
import soot.jimple.Stmt;

import java.util.*;

/**
 * Created by liture on 2021/9/20 1:57 下午
 */
public class CHACallGraphBuilder {

    private static CHACallGraphBuilder builder;

    public static CHACallGraphBuilder v() {
        if (builder == null) {
            builder = new CHACallGraphBuilder();
        }
        return builder;
    }

    private Hierarchy cha;

    private CHACallGraphBuilder() {
        cha = new Hierarchy();
    }

    /**
     * Lecture 7, 26页给出的Dispatch函数
     * @param sootClass
     * @param method
     * @return 如果没有满足的method被找到，返回null
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
                    // test parameter 1 by 1.
                    Set<String> parameterQuoteSet = new HashSet<>();;
                    for(Type t : method.getParameterTypes()) {
                        parameterQuoteSet.add(t.toQuotedString());
                    }

                    for (int i = 0; i < method.getParameterCount(); i++) {
                        String tqs = m.getParameterType(i).toQuotedString();
                        // match all
                        if(parameterQuoteSet.contains(tqs)) {
                            if(i==method.getParameterCount()-1) {
                                return method;
                            }
                        } else {
                            // do not match
                            break;
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

    /**
     * Lecture 7, 33页给出的Resolve函数
     * @param unit
     * @return
     */
    private Set<SootMethod> resolveCalleeOf(Unit unit) {
        Stmt stmt = (Stmt) unit;
        // get call-site's method by it's variable type
        InvokeExpr invoke = stmt.getInvokeExpr();
        SootMethod m = invoke.getMethod();
        SootClass cm = m.getDeclaringClass();

        CallKind callKind = CallKind.getCallKind(unit);
        // static invoke
        if (callKind == CallKind.STATIC) {
            return Collections.singleton(invoke.getMethod());
        }
        // special invoke
        if (callKind == CallKind.SPECIAL) {
            SootMethod dispatch = dispatch(cm, m);
            return dispatch == null ? Collections.emptySet() : Collections.singleton(dispatch);
        }
        // interface invoke / virtual invoke
        List<SootClass> classes;
        if (cm.isInterface()) {
            classes = cha.getSubinterfacesOfIncluding(cm);
        } else {
            classes = cha.getSubclassesOfIncluding(cm);
        }
        Set<SootMethod> result = new HashSet<>();
        for (SootClass aClass : classes) {
            SootMethod dispatch = dispatch(aClass, m);
            if (dispatch != null) {
                result.add(dispatch);
            }
        }
        return result;
    }

    public void build(JimpleCallGraph cg) {
        Collection<SootMethod> entries = cg.getEntryMethods();

        Queue<SootMethod> workList = new LinkedList<>(entries);
        while (!workList.isEmpty()) {
            SootMethod method = workList.poll();
            if (!method.hasActiveBody()) {
                continue;
            }
            Collection<Unit> callSites = cg.getCallSiteIn(method);
            for (Unit callSite : callSites) {
                // 解析被调用函数 callee
                Set<SootMethod> calleeMethods = resolveCalleeOf(callSite);
                for (SootMethod callee : calleeMethods) {
                    // 如果是新的可达方法，那么入队列
                    if (!cg.contains(callee)) {
                        workList.add(callee);
                    }
                    // 添加一条调用边
                    cg.addEdge(callSite, callee, CallKind.getCallKind(callSite));
                }
            }
        }
    }
}
