package com.canliture.soot.ass4.pta.stmt;

import com.canliture.soot.ass4.pta.elem.CallSite;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:08 下午
 *
 * 方法调用; 在本次作业中并不是你需要关心的.
 *
 * 在这里不详细介绍 fixme
 */
public class Call extends Statement {

    private CallSite callSite;

    public Call(CallSite callSite) {
        this.callSite = callSite;
    }

    public CallSite getCallSite() {
        return callSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Call call = (Call) o;
        return Objects.equals(callSite, call.callSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(callSite);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + callSite.toString();
    }
}
