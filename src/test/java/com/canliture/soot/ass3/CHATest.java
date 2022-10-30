package com.canliture.soot.ass3;

import com.canliture.soot.InterBaseTest;
import soot.Transformer;

import java.util.Arrays;
import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

/**
 * Created by liture on 2021/9/20 5:04 下午
 */
public class CHATest extends InterBaseTest {

    @Override
    public List<String> getProcessDirs() {
        return Collections.singletonList("target/test-classes/");
    }

    @Override
    public List<String> getExcluded() {
        List<String> excluded = new LinkedList<>(super.getExcluded());
        excluded.add("ass1.*");
        excluded.add("ass2.*");
        excluded.add("ass4.*");
        excluded.add("ass5.*");
        return excluded;
    }

    @Override
    public String getPhaseNameOfPack() {
        return "wjtp";
    }

    @Override
    public String getPhaseNameOfTransformer() {
        return "wjtp.cg_cha";
    }

    @Override
    public Transformer getTransformer() {
        return new CHATransformer();
    }
}
