package com.canliture.soot;

import com.canliture.soot.ass1.IntraCPTransformer;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import soot.*;
import soot.options.Options;

import java.util.Arrays;
import java.util.List;

/**
 * Created by liture on 2021/9/20 2:16 上午
 */
public abstract class BaseTest {

    public abstract List<String> getProcessDirs();

    public abstract String getPhaseNameOfPack();

    public abstract String getPhaseNameOfTransformer();

    public abstract Transformer getTransformer();

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
        Options.v().set_process_dir(getProcessDirs());

        // 保留变量原始的名字
        Options.v().setPhaseOption("jb", "use-original-names:true");
        // 输出Jimple IR文件到sootOutput目录中，方便调试查看
        Options.v().set_output_format(Options.output_format_jimple);

        // 加载所有类
        Scene.v().loadNecessaryClasses();
    }

    @Test
    public void test() {
        // user-defined transform
        String packPhaseName = getPhaseNameOfPack();
        String transformerPhaseName = getPhaseNameOfTransformer();
        Transformer transformer = getTransformer();
        Transform transform = new Transform(transformerPhaseName, transformer);
        PackManager.v().getPack(packPhaseName).add(transform);
        // 只分析应用类
        for (SootClass appClazz : Scene.v().getApplicationClasses()) {
            for (SootMethod method : appClazz.getMethods()) {
                Body body = method.retrieveActiveBody();
                PackManager.v().getPack(packPhaseName).apply(body);
            }
        }
    }

    @After
    public void output() {
        // 输出到 Jimple IR到sootOutput目录中
        PackManager.v().writeOutput();
    }
}
