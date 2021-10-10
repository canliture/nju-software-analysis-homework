package com.canliture.soot.ass5.pta.elem;

import soot.jimple.Stmt;
import soot.tagkit.LineNumberTag;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:07 下午
 *
 * 调用点
 *
 * r = x.k(arg, ...)
 * or
 * x.k(arg, ...)
 */
public class CallSite {

    private final Stmt callSite;

    /**
     * x
     */
    private final Variable receiver;

    /**
     * r
     */
    private final Variable ret;

    public CallSite(Stmt callSite) {
        this(callSite, null);
    }

    public CallSite(Stmt callSite, Variable receiver) {
        this(callSite, receiver, null);
    }

    /**
     * @param callSite
     * @param receiver receiver object
     * @param ret return object
     */
    public CallSite(Stmt callSite, Variable receiver, Variable ret) {
        this.callSite = callSite;
        this.receiver = receiver;
        this.ret = ret;
    }

    public Stmt getCallSite() {
        return callSite;
    }

    public Variable getReceiver() {
        return receiver;
    }

    public Variable getRet() {
        return ret;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CallSite callSite1 = (CallSite) o;
        return Objects.equals(callSite, callSite1.callSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callSite);
    }

    @Override
    public String toString() {
        return callSite.toString() + "@" + callSite.getTag(LineNumberTag.IDENTIFIER);
    }
}
