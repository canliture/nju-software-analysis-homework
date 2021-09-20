package com.canliture.soot.ass3;

import soot.SootMethod;
import soot.Unit;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 4:36 下午
 */
public class CallEdge {

    private CallKind callKind;

    private Unit callSite;

    private SootMethod callee;

    public CallEdge(CallKind callKind, Unit callSite, SootMethod callee) {
        this.callKind = callKind;
        this.callSite = callSite;
        this.callee = callee;
    }

    public CallKind getCallKind() {
        return callKind;
    }

    public Unit getCallSite() {
        return callSite;
    }

    public SootMethod getCallee() {
        return callee;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallEdge callEdge = (CallEdge) o;
        return Objects.equals(callSite, callEdge.callSite) && Objects.equals(callee, callEdge.callee);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callSite, callee);
    }
}
