package com.canliture.soot;

import org.junit.Test;
import soot.*;

import java.util.List;

/**
 * Created by liture on 2021/9/20 5:05 下午
 */
public abstract class InterBaseTest extends IntraBaseTest {

    @Test
    @Override
    public void test() {
        // user-defined transform
        String packPhaseName = getPhaseNameOfPack();
        String transformerPhaseName = getPhaseNameOfTransformer();
        Transformer transformer = getTransformer();
        Transform transform = new Transform(transformerPhaseName, transformer);
        PackManager.v().getPack(packPhaseName).add(transform);
        PackManager.v().getPack(packPhaseName).apply();
    }
}
