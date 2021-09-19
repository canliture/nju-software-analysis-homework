package com.canliture.soot.ass1;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import soot.*;
import soot.options.Options;

import java.util.Arrays;

/**
 * Created by liture on 2021/9/19 4:29 下午
 */
public class IntraConstantPropagationTest {

    @Before
    public void initializeSoot() {
        Options.v().set_whole_program(true);
        Options.v().set_allow_phantom_refs(true);

        // 只ass代码
        Options.v().set_exclude(Arrays.asList("com.canliture.*"));
        Options.v().set_no_bodies_for_excluded(true);

        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Arrays.asList("target/test-classes"));

        Options.v().setPhaseOption("jb", "use-original-names:true");
        Options.v().set_output_format(Options.output_format_jimple);

        Scene.v().loadNecessaryClasses();
    }

    @Test
    public void test() {
        IntraCPTransformer transformer = new IntraCPTransformer();
        // user-defined transform
        Transform transform = new Transform("jtp.intra_cp", transformer);
        PackManager.v().getPack("jtp").add(transform);
        // 只分析应用类
        for (SootClass appClazz : Scene.v().getApplicationClasses()) {
            for (SootMethod method : appClazz.getMethods()) {
                Body body = method.retrieveActiveBody();
                PackManager.v().getPack("jtp").apply(body);
            }
        }
    }

    @After
    public void output() {
        PackManager.v().writeOutput();
    }
}
