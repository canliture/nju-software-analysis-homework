package com.canliture.soot.ass2;

import com.canliture.soot.IntraBaseTest;
import soot.Transformer;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liture on 2021/9/20 2:06 上午
 */
public class DeadCodeDetectionTest extends IntraBaseTest {

    @Override
    public List<String> getProcessDirs() {
        return Arrays.asList("target/test-classes");
    }

    @Override
    public List<String> getExcluded() {
        List<String> excluded = new LinkedList<>(super.getExcluded());
        excluded.add("ass1.*");
        excluded.add("ass3.*");
        excluded.add("ass4.*");
        excluded.add("ass5.*");
        return excluded;
    }

    @Override
    public String getPhaseNameOfPack() {
        return "jtp";
    }

    @Override
    public String getPhaseNameOfTransformer() {
        return "jtp.dead_code_detect";
    }

    @Override
    public Transformer getTransformer() {
        return new DeadCodeTransformer();
    }
}
