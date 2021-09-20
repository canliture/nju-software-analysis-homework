package com.canliture.soot.ass3;

import soot.SootClass;
import soot.SootMethod;
import soot.Unit;

import java.util.Collections;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 1:57 下午
 */
public class CHACallGraphBuilder {

    private static CHACallGraphBuilder builder;

    public static CHACallGraphBuilder v() {
        if (builder == null) {
            builder = new CHACallGraphBuilder();
        }
        return builder;
    }

    private CHACallGraphBuilder() { }

    /**
     * Lecture 7, 26页给出的Resolve函数
     * @param sootClass
     * @param method
     * @return 如果没有满足的method被找到，返回null
     */
    public SootMethod dispatch(SootClass sootClass, SootMethod method) {
        // todo
        return null;
    }

    /**
     * @param unit
     * @return
     */
    public Set<SootMethod> resolveCalleeOf(Unit unit) {
        // todo
        return Collections.emptySet();
    }

    public void build(JimpleCallGraph cg) {
        // todo
    }
}
