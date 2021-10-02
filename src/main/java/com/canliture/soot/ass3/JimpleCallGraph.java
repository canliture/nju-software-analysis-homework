package com.canliture.soot.ass3;

import soot.*;
import soot.jimple.Stmt;

import java.util.*;

/**
 * Created by liture on 2021/9/20 1:44 下午
 */
public class JimpleCallGraph {

    /**
     * entries
     */
    private Collection<SootMethod> entries;

    /**
     * 可达方法
     */
    private Set<SootMethod> reachableMethods = new HashSet<>();

    /**
     * callee -> callers
     */
    private Map<SootMethod, Set<CallEdge>> callee2caller = new HashMap<>();

    /**
     * caller -> callees
     */
    private Map<SootMethod, Set<CallEdge>> caller2callee = new HashMap<>();

    private Map<Unit, SootMethod> unit2Owner = new HashMap<>();

    public JimpleCallGraph() {
        for (SootClass clazz : Scene.v().getApplicationClasses()) {
            for (SootMethod method : clazz.getMethods()) {
                Body body = method.retrieveActiveBody();
                if (body != null) {
                    for (Unit unit : body.getUnits()) {
                        unit2Owner.put(unit, method);
                    }
                }
            }
        }
    }

    /**
     * @return 返回被分析程序的entry方法，作业中只需返回main方法即可
     */
    public Collection<SootMethod> getEntryMethods() {
        if (entries != null) {
            return entries;
        }
        entries = new LinkedList<>();
        for (SootClass clazz : Scene.v().getApplicationClasses()) {
            for (SootMethod method : clazz.getMethods()) {
                if ("main".equals(method.getName())) {
                    entries.add(method);
                }
            }
        }
        // 初始情况下，entry methods总是可达的
        reachableMethods.addAll(entries);
        return entries;
    }

    /**
     * @param method
     * @return 返回给定方法内的的所有调用点
     */
    public Collection<Unit> getCallSiteIn(SootMethod method) {
        List<Unit> callSites = new LinkedList<>();
        if (method.hasActiveBody()) {
            Body body = method.getActiveBody();
            for (Unit unit : body.getUnits()) {
                Stmt stmt = (Stmt) unit;
                if (stmt.containsInvokeExpr()) {
                    callSites.add(stmt);
                }
            }
        }
        return callSites;
    }

    /**
     * @param method
     * @return 返回是否给定method在调用图上是可达的；entry Method总是可达的
     */
    public boolean contains(SootMethod method) {
        return reachableMethods.contains(method);
    }

    public boolean contains(Unit callSite, SootMethod callee) {
        Collection<Unit> callSites = getCallSiteIn(callee);
        for (Unit site : callSites) {
            if (site == callSite) {
                return true;
            }
        }
        return false;
    }

    /**
     * 添加一条调用遍到调用图中
     * @param callSite 调用点
     * @param callee 被调用method
     * @param callKind 调用类型
     * @return
     */
    public boolean addEdge(Unit callSite, SootMethod callee, CallKind callKind) {
        // 维护 Reachable Methods
        reachableMethods.add(callee);

        CallEdge callEdge = new CallEdge(callKind, callSite, callee);

        // 维护两个表

        Set<CallEdge> callers = callee2caller.computeIfAbsent(callee, k -> new HashSet<>());
        boolean ret = callers.add(callEdge);

        SootMethod caller = unit2Owner.get(callSite);
        Set<CallEdge> callees = caller2callee.computeIfAbsent(caller, k -> new HashSet<>());
        callees.add(callEdge);

        return ret;
    }

    /**
     * @param method
     * @return 返回method调用出去的边
     */
    public Set<CallEdge> getCallOutOf(SootMethod method) {
        Set<CallEdge> result = caller2callee.computeIfAbsent(method, k -> new HashSet<>());
        return Collections.unmodifiableSet(result);
    }

    /**
     * @param method
     * @return 返回method调用进来的边
     */
    public Set<CallEdge> getCallInOf(SootMethod method) {
        Set<CallEdge> result = callee2caller.computeIfAbsent(method, k -> new HashSet<>());
        return Collections.unmodifiableSet(result);
    }
}
