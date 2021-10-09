package com.canliture.soot.ass2;

import soot.Unit;
import soot.toolkits.scalar.Pair;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liture on 2021/9/19 10:14 下午
 *
 * 表示控制流边的集合，我们需要它来帮助检测 unreachable branches
 */
public class EdgeSet {

    private Set<Pair<Unit, Unit>> edgeSet;

    public EdgeSet() {
        this(new HashSet<>());
    }

    public EdgeSet(Set<Pair<Unit, Unit>> edgeSet) {
        this.edgeSet = edgeSet;
    }

    /**
     * 添加一条控制流边
     * @param from
     * @param to
     */
    public void addEdge(Unit from, Unit to) {
        edgeSet.add(new Pair<>(from, to));
    }

    /**
     * @param from
     * @param to
     * @return 是否包含一条控制流边
     */
    boolean containsEdge(Unit from, Unit to) {
        return edgeSet.contains(new Pair<>(from, to));
    }
}
