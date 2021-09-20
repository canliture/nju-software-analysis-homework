package com.canliture.soot.ass3;

import soot.Scene;
import soot.SceneTransformer;
import soot.SootClass;
import soot.SootMethod;

import java.util.Map;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 5:08 下午
 */
public class CHATransformer extends SceneTransformer {

    @Override
    protected void internalTransform(String phaseName, Map<String, String> options) {
        CHACallGraphBuilder cgBuilder = CHACallGraphBuilder.v();
        JimpleCallGraph cg = new JimpleCallGraph();
        cgBuilder.build(cg);

        for (SootClass clazz : Scene.v().getApplicationClasses()) {
            for (SootMethod method : clazz.getMethods()) {
                StringBuilder buff = new StringBuilder();
                // basic information
                buff.append(method.getSignature())
                    .append(": \n")
                    .append("\t ").append(cg.contains(method) ? "Reachable" : "Unreachable")
                    .append("\n");

                // call edge
                Set<CallEdge> edgeSet = cg.getCallOutOf(method);
                for (CallEdge callEdge : edgeSet) {
                    buff.append("\t ").append(callEdge).append("\n");
                }
                buff.append("\n");
                System.out.println(buff);
            }
        }
    }
}
