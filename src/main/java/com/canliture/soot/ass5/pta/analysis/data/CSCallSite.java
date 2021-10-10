package com.canliture.soot.ass5.pta.analysis.data;

import com.canliture.soot.ass5.pta.analysis.context.Context;
import com.canliture.soot.ass5.pta.elem.CallSite;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 12:38 上午
 *
 * 表示指针分析中的上下文敏感的调用点
 */
public class CSCallSite extends ContextSensitive {

    private CallSite callSite;

    public CSCallSite(Context context, CallSite callSite) {
        super(context);
        this.callSite = callSite;
    }

    /**
     * @return 具体的调用点
     */
    public CallSite getCallSite() {
        return callSite;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CSCallSite that = (CSCallSite) o;
        return Objects.equals(callSite, that.callSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), callSite);
    }

    @Override
    public String toString() {
        return super.toString() + ":" + callSite;
    }
}
