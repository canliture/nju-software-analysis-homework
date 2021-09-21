package com.canliture.soot.ass4;

import com.canliture.soot.InterBaseTest;
import com.canliture.soot.ass4.pta.analysis.ci.PointerAnalysisTransformer;
import soot.Transformer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liture on 2021/9/22 1:13 上午
 */
public class PointerAnalysisTest extends InterBaseTest {

    @Override
    public List<String> getProcessDirs() {
        return Arrays.asList("target/test-classes");
    }

    @Override
    public List<String> getExcluded() {
        List<String> excluded = new LinkedList<>(super.getExcluded());
        excluded.add("ass1.*");
        excluded.add("ass2.*");
        excluded.add("ass3.*");
        return excluded;
    }

    @Override
    public String getPhaseNameOfPack() {
        return "wjtp";
    }

    @Override
    public String getPhaseNameOfTransformer() {
        return "wjtp.pointer_analysis";
    }

    @Override
    public Transformer getTransformer() {
        return new PointerAnalysisTransformer();
    }
}
