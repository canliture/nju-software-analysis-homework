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
        // 加载JDK到class path中
        Options.v().set_whole_program(true);
        // 容错
        Options.v().set_allow_phantom_refs(true);

        // 不分析我们的测试代码
        Options.v().set_exclude(Arrays.asList("com.canliture.*"));
        Options.v().set_no_bodies_for_excluded(true);

        Options.v().set_prepend_classpath(true);
        Options.v().set_process_dir(Arrays.asList("target/test-classes"));

        // 保留变量原始的名字
        Options.v().setPhaseOption("jb", "use-original-names:true");
        // 输出Jimple IR文件到sootOutput目录中，方便调试查看
        Options.v().set_output_format(Options.output_format_jimple);

        // 加载所有类
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
        // 输出到 Jimple IR到sootOutput目录中
        PackManager.v().writeOutput();
    }
}
