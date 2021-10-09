package com.canliture.soot.ass5.pta.analysis.context;

import com.canliture.soot.ass5.pta.analysis.data.CSCallSite;
import com.canliture.soot.ass5.pta.analysis.data.CSMethod;
import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.canliture.soot.ass5.pta.elem.Method;

/**
 * Created by liture on 2021/10/10 12:57 上午
 *
 * 1-type sensitivity
 */
public class OneTypeSelector implements ContextSelector {

    @Override
    public Context selectContext(CSCallSite csCallSite, Method method) {
        return null;
    }

    @Override
    public Context selectContext(CSCallSite csCallSite, CSObj csObj, Method method) {
        return null;
    }

    @Override
    public Context selectHeapContext(CSMethod csMethod, Object o) {
        return null;
    }
}
