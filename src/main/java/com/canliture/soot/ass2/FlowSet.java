package com.canliture.soot.ass2;

import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

/**
 * Created by liture on 2021/9/19 9:58 下午
 *
 * 用于表示live variables (soot.Local) 的集合；
 * 每条语句的 IN 和 OUT 的数据流用当前类来表示
 */
public class FlowSet<T> {

    private Set<T> delegateSet;

    public FlowSet() {
        this(new HashSet<>());
    }

    public FlowSet(Set<T> delegateSet) {
        this.delegateSet = delegateSet;
    }

    /**
     * 是否包含元素
     * @param t
     * @return
     */
    public boolean contains(T t) {
        return delegateSet.contains(t);
    }

    /**
     * 添加T类型的数据到当前set中
     * @param t
     * @return 是否改变了当前set的内容
     */
    public boolean add(T t) {
        return delegateSet.add(t);
    }

    /**
     * 从set中移除t
     * @param t
     * @return 是否remove操作改变了当前set的内容
     */
    public boolean remove(T t) {
        return delegateSet.remove(t);
    }

    /**
     * 并集另一个set的内容到当前set
     * @param another
     * @return 当前set
     */
    public FlowSet<T> union(FlowSet<T> another) {
        delegateSet.addAll(another.delegateSet);
        return this;
    }

    /**
     * 交集另一个集合到当前集合
     * @param another
     * @return 返回当前集合
     */
    public FlowSet<T> intersect(FlowSet<T> another) {
        Iterator<T> iterator = delegateSet.iterator();
        while (iterator.hasNext()) {
            T t = iterator.next();
            if (!another.delegateSet.contains(t)) {
                iterator.remove();
            }
        }
        return this;
    }

    /**
     * @return 返回当前集合的一个新创建的拷贝
     */
    public FlowSet<T> duplicate() {
        FlowSet<T> result = new FlowSet<>();
        result.delegateSet.addAll(delegateSet);
        return result;
    }

    /**
     * 设置当前集合的内容与另一个集合内容一样
     * @param another
     * @return 返回当前集合
     */
    public FlowSet<T> setTo(FlowSet<T> another) {
        delegateSet.clear();
        delegateSet.addAll(another.delegateSet);
        return this;
    }
}
