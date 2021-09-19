package com.canliture.soot.ass2;

import soot.Unit;

/**
 * Created by liture on 2021/9/19 10:14 下午
 *
 * 表示控制流边的集合，我们需要它来帮助检测 unreachable branches
 */
public class EdgeSet {

    /**
     * 添加一条控制流边
     * @param from
     * @param to
     */
    public void addEdge(Unit from, Unit to) {
        // todo
    }

    /**
     * @param from
     * @param to
     * @return 是否包含一条控制流边
     */
    boolean containsEdge(Unit from, Unit to) {
        // todo
        return false;
    }
}
