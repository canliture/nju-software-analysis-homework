package com.canliture.soot.ass4.pta.analysis.ci;

import com.canliture.soot.ass4.pta.elem.Obj;

import java.util.Collections;
import java.util.Iterator;

/**
 * Created by liture on 2021/9/20 11:10 下午
 *
 * 指向集合(pts)
 */
public class PointsToSet implements Iterable<Obj> {

    /**
     * 往pts中添加指向的对象
     * @param obj
     * @return 返回false，如果给定的obj早已存在；否则返回true
     */
    public boolean addObject(Obj obj) {
        // todo
        return false;
    }

    /**
     * @return pts是否为空
     */
    public boolean isEmpty() {
        // todo
        return true;
    }

    /**
     * @return pts集合的迭代器
     */
    @Override
    public Iterator<Obj> iterator() {
        // todo
        return Collections.emptyIterator();
    }
}
