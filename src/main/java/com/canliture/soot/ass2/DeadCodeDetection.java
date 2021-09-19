package com.canliture.soot.ass2;

import soot.Body;
import soot.Unit;
import soot.jimple.AssignStmt;
import soot.toolkits.graph.DirectedGraph;

import java.util.Collections;
import java.util.Set;

/**
 * Created by liture on 2021/9/19 9:56 下午
 */
public class DeadCodeDetection {

    /**
     * 对给定body开始死代码检测
     * @param b
     * @return 返回检测到的死代码
     */
    public Set<Unit> findDeadCode(Body b) {
        // todo
        return Collections.emptySet();
    }

    /**
     * @param cfg
     * @return 返回给定cfg的entry，我们需要这个entry来对cfg进行遍历
     */
    public Unit getEntry(DirectedGraph<Unit> cfg) {
        // todo
        return null;
    }

    /**
     * 使用本方法来检测死赋值(dead assignment)
     * 如果有副作用，则不认为它可以被remove优化掉，即时赋值的左侧是not live的
     * @param stmt
     * @return 是否一个赋值可能有副作用(side-effect)
     */
    public boolean mayHaveSideEffect(AssignStmt stmt) {
        // todo
        return true;
    }

    /**
     * 迭代给定cfg的语句来检测不可达的IfStmt分支；
     *
     * 如果if语句的条件值总是为true，那么应该添加false分支的边到返回的EdgeSet中；
     * 对于if条件值总是为false，类似地操作
     * @param body
     * @param cfg
     * @return
     */
    private EdgeSet findUnreachableBranches(Body body, DirectedGraph<Unit> cfg) {
        // todo
        return new EdgeSet();
    }

    /**
     * 从entry节点开始遍历给定的cfg, 遍历的过程中，应该跳过给定的 unreachableEdgeSet 中不可达的分支
     * @param cfg
     * @param unreachableEdgeSet
     * @return
     */
    private Set<Unit> findUnreachableCode(DirectedGraph<Unit> cfg, EdgeSet unreachableEdgeSet) {
        // todo
        return Collections.emptySet();
    }
}
