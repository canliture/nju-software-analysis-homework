package com.canliture.soot.ass3;

import soot.SceneTransformer;
import java.util.Map;

/**
 * Created by liture on 2021/9/20 5:08 下午
 */
public class CHATransformer extends SceneTransformer {

    @Override
    protected void internalTransform(String phaseName, Map<String, String> options) {
        CHACallGraphBuilder cgBuilder = CHACallGraphBuilder.v();
        JimpleCallGraph cg = new JimpleCallGraph();
        cgBuilder.build(cg);
    }
}
