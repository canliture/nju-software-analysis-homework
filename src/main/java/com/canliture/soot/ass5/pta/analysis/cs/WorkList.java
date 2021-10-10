package com.canliture.soot.ass5.pta.analysis.cs;

import soot.toolkits.scalar.Pair;
import java.util.LinkedList;
import java.util.Queue;

/**
 * Created by liture on 2021/9/20 11:11 下午
 *
 * 指针分析算法中的 WorkList
 */
public class WorkList {

    private final Queue<Pair<Pointer, PointsToSet>> queue;

    public WorkList() {
        queue = new LinkedList<>();
    }

    /**
     * 添加一条 <p, pts> 到workList
     * @param p 指针
     * @param pts 一个指向集合，它所表示的对象被传播到这个指针的pts
     */
    public void addPointerEntry(Pointer p, PointsToSet pts) {
        queue.add(new Pair<>(p, pts));
    }

    /**
     * @return workList是否为空
     */
    public boolean isEmpty() {
        return queue.isEmpty();
    }

    /**
     * @return 从workList取出一条 <p, pts>
     */
    public Pair<Pointer, PointsToSet> remove() {
        return queue.poll();
    }
}
