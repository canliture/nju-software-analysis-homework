package com.canliture.soot.ass2;

import soot.Body;
import soot.BodyTransformer;
import soot.Unit;

import java.util.Map;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 2:28 上午
 */
public class DeadCodeTransformer extends BodyTransformer {

    @Override
    protected void internalTransform(Body b, String phaseName, Map<String, String> options) {
        DeadCodeDetection deadCodeDetection = new DeadCodeDetection();
        Set<Unit> deadCodes = deadCodeDetection.findDeadCode(b);
        System.out.println(String.format("- - - - - Dead Code Of Method %s - - - - -", b.getMethod().getName()));
        deadCodes.forEach(System.out::println);
        System.out.println(String.format("- - - - - End of Dead Code Of Method %s - - - - -\n\n", b.getMethod().getName()));
    }
}
