package com.canliture.soot.ass5.pta.analysis.context;

import com.canliture.soot.ass5.pta.analysis.data.CSCallSite;
import com.canliture.soot.ass5.pta.analysis.data.CSMethod;
import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.canliture.soot.ass5.pta.elem.Method;

/**
 * Created by liture on 2021/10/10 12:58 上午
 *
 * 2-call-site sensitivity
 */
public class TwoCallSelector implements ContextSelector {

    @Override
    public Context selectContext(CSCallSite csCallSite, Method method) {
        Context preContext = csCallSite.getContext();
        switch (preContext.depth()) {
            case 0:
                return new TwoContext<>(csCallSite.getCallSite(), null);
            case 1:
                return new TwoContext<>(preContext.element(1), csCallSite.getCallSite());
            case 2:
                return new TwoContext<>(preContext.element(2), csCallSite.getCallSite());
            default:
                throw new IllegalStateException();
        }
    }

    @Override
    public Context selectContext(CSCallSite csCallSite, CSObj csObj, Method method) {
        return selectContext(csCallSite, method);
    }

    @Override
    public Context selectHeapContext(CSMethod csMethod, Object o) {
        throw new UnsupportedOperationException();
    }
}
