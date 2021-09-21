package com.canliture.soot.ass4.pta.analysis.ci;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;

import java.util.Map;

/**
 * Created by liture on 2021/9/20 11:12 下午
 */
public class PointerAnalysisTransformer extends SceneTransformer {

    @Override
    protected void internalTransform(String phaseName, Map<String, String> options) {
        SootMethod entry = null;
        outer:
        for (SootClass clazz : Scene.v().getApplicationClasses()) {
            for (SootMethod method : clazz.getMethods()) {
                if ("main".equals(method.getName())) {
                    entry = method;
                    // 分析单entry
                    break outer;
                }
            }
        }

        if (entry != null) {
            PointerAnalysis pointerAnalysis = new PointerAnalysis(entry);
            pointerAnalysis.solve();

            PointerFlowGraph PFG = pointerAnalysis.PFG;
            System.out.println(PFG);
        }
    }
}
