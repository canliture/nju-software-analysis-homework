package com.canliture.soot.ass5.pta.analysis.context;

import com.canliture.soot.ass5.pta.analysis.data.CSCallSite;
import com.canliture.soot.ass5.pta.analysis.data.CSMethod;
import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.canliture.soot.ass5.pta.elem.Method;

/**
 * Created by liture on 2021/10/10 12:53 上午
 *
 * 1-call-site sensitivity
 */
public class OneCallSelector implements ContextSelector {

    @Override
    public Context selectContext(CSCallSite csCallSite, Method method) {
        return new OneContext<>(csCallSite.getCallSite());
    }

    @Override
    public Context selectContext(CSCallSite csCallSite, CSObj csObj, Method method) {
        return new OneContext<>(csCallSite.getCallSite());
    }

    @Override
    public Context selectHeapContext(CSMethod csMethod, Object o) {
        throw new UnsupportedOperationException();
    }
}
