package com.canliture.soot.ass3;

import soot.SootMethod;
import soot.Unit;

import java.util.Collection;
import java.util.Collections;

/**
 * Created by liture on 2021/9/20 1:44 下午
 */
public class JimpleCallGraph {

    /**
     * @return 返回被分析程序的entry方法，作业中只需返回main方法即可
     */
    public Collection<SootMethod> getEntryMethods() {
        // todo
        return Collections.emptyList();
    }

    /**
     * @param callee
     * @return 返回所有调用给定方法的调用点
     */
    public Collection<Unit> getCallSiteIn(SootMethod callee) {
        // todo
        return Collections.emptyList();
    }

    /**
     * @param method
     * @return 返回是否给定method在调用图上是可达的；entry Method总是可达的
     */
    boolean contains(SootMethod method) {
        // todo
        return false;
    }

    /**
     * 添加一条调用遍到调用图中
     * @param callSite 调用点
     * @param callee 被调用method
     * @param callKid 调用类型
     * @return
     */
    boolean addEdge(Unit callSite, SootMethod callee, CallKid callKid) {
        // todo
        return false;
    }
}
