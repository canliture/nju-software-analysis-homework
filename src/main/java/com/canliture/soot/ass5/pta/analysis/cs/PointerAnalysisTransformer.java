package com.canliture.soot.ass5.pta.analysis.cs;

import com.canliture.soot.ass5.pta.analysis.context.*;
import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;

import java.util.*;

/**
 * Created by liture on 2021/10/10 3:40 下午
 */
public class PointerAnalysisTransformer extends SceneTransformer {
    
    @Override
    protected void internalTransform(String phaseName, Map<String, String> options) {
        List<SootMethod> entries = new LinkedList<>();
        for (SootClass clazz : Scene.v().getApplicationClasses()) {
            for (SootMethod method : clazz.getMethods()) {
                if ("main".equals(method.getName())) {
                    entries.add(method);
                }
            }
        }

        for (SootMethod entry : entries) {
            ContextSelector selector = getSelector();
            PointerAnalysis pointerAnalysis = new PointerAnalysis(entry, selector);
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

    protected ContextSelector getSelector() {
        // fixme change the selector implementation for verification.
        //return new OneCallSelector();
        return new TwoCallSelector();

        //return new OneObjectSelector();
        //return new TwoObjectSelector();

        //return new OneTypeSelector();
        //return new TwoTypeSelector();
    }
}
