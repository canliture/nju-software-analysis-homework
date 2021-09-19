package com.canliture.soot.ass2.ass1;

import soot.Body;
import soot.BodyTransformer;
import soot.Unit;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.UnitGraph;

import java.util.Map;

/**
 * Created by liture on 2021/9/19 4:46 下午
 */
public class IntraCPTransformer extends BodyTransformer {

    @Override
    protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
        UnitGraph unitGraph = new BriefUnitGraph(b);
        IntraConstantPropagation propagation = new IntraConstantPropagation(unitGraph);
        propagation.doAnalysis();
    }
}
