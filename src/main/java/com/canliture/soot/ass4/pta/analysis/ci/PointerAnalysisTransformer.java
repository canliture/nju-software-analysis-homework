package com.canliture.soot.ass4.pta.analysis.ci;

import soot.*;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

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
            System.out.println("======== PFG ========");
            Set<Pointer> pointerSet = new LinkedHashSet<>();
            pointerSet.addAll(PFG.varMap.values());
            pointerSet.addAll(PFG.instanceFieldMap.values());
            for (Pointer pointer : pointerSet) {
                StringBuilder buff = new StringBuilder();
                buff.append(pointer).append("\n");
                buff.append("\t pts: ").append(pointer.getPointsToSet()).append("\n");
                buff.append("\t edges: ").append(PFG.getSuccessorOf(pointer)).append("\n");
                System.out.println(buff);
            }
            System.out.println("======== End of PFG ========\n");
        }
    }
}
