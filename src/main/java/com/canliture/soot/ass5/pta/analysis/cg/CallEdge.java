package com.canliture.soot.ass5.pta.analysis.cg;

import com.canliture.soot.ass5.pta.analysis.data.CSCallSite;
import com.canliture.soot.ass5.pta.analysis.data.CSMethod;
import java.util.Objects;

/**
 * Created by liture on 2021/9/20 4:36 下午
 */
public class CallEdge {

    private CallKind callKind;

    private CSCallSite callSite;

    private CSMethod callee;

    public CallEdge(CallKind callKind, CSCallSite callSite, CSMethod callee) {
        this.callKind = callKind;
        this.callSite = callSite;
        this.callee = callee;
    }

    public CallKind getCallKind() {
        return callKind;
    }

    public CSCallSite getCallSite() {
        return callSite;
    }

    public CSMethod getCallee() {
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

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        buff.append(callSite)
            .append(" -> ")
            .append(callee);
        return buff.toString();
    }
}
