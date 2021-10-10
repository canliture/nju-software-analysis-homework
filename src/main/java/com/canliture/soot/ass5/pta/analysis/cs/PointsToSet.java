package com.canliture.soot.ass5.pta.analysis.cs;

import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.google.common.collect.Iterators;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 11:10 下午
 *
 * 指向集合(pts)
 */
public class PointsToSet implements Iterable<CSObj> {

    private Set<CSObj> pts;

    public PointsToSet() {
        this(new HashSet<>());
    }

    public PointsToSet(Set<CSObj> pts) {
        this.pts = pts;
    }

    public static PointsToSet singleton(CSObj o) {
        PointsToSet pointsToSet = new PointsToSet();
        pointsToSet.addObject(o);
        return pointsToSet;
    }

    /**
     * 往pts中添加指向的对象
     * @param obj
     * @return 返回false，如果给定的obj早已存在；否则返回true
     */
    public boolean addObject(CSObj obj) {
        return pts.add(obj);
    }

    /**
     * @return pts是否为空
     */
    public boolean isEmpty() {
        return pts.isEmpty();
    }

    /**
     * @return pts集合的迭代器
     */
    @Override
    public Iterator<CSObj> iterator() {
        return Iterators.unmodifiableIterator(pts.iterator());
    }

    /**
     * @param pts1
     * @param pts2
     * @return pts1 U pts2
     */
    public static PointsToSet union(PointsToSet pts1, PointsToSet pts2) {
        PointsToSet result = new PointsToSet();
        result.pts.addAll(pts1.pts);
        result.pts.addAll(pts2.pts);
        return result;
    }

    /**
     * 合并另一个pts到当前
     * @param pts
     */
    public void union(PointsToSet pts) {
        for (CSObj o : pts) {
            addObject(o);
        }
    }

    /**
     * @param pts1
     * @param pts2
     * @return pts1 - pts2
     */
    public static PointsToSet difference(PointsToSet pts1, PointsToSet pts2) {
        PointsToSet result = new PointsToSet();
        result.pts.addAll(pts1.pts);
        result.difference(pts2);
        return result;
    }

    /**
     * self = self - pts
     * @param pts
     */
    public void difference(PointsToSet pts) {
        for (CSObj o : pts) {
            if (this.pts.contains(o)) {
                this.pts.remove(o);
            }
        }
    }

    @Override
    public String toString() {
        return pts.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        PointsToSet that = (PointsToSet) o;
        return Objects.equals(pts, that.pts);
    }

    @Override
    public int hashCode() {
        return Objects.hash(pts);
    }
}
