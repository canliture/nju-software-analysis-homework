package com.canliture.soot.ass2;

/**
 * Created by liture on 2021/9/19 9:58 下午
 *
 * 用于表示live variables (soot.Local) 的集合；
 * 每条语句的 IN 和 OUT 的数据流用当前类来表示
 */
public class FlowSet<T> {

    /**
     * 添加T类型的数据到当前set中
     * @param t
     * @return 是否改变了当前set的内容
     */
    public boolean add(T t) {
        // todo
        return false;
    }

    /**
     * 从set中移除t
     * @param t
     * @return 是否remove操作改变了当前set的内容
     */
    public boolean remove(T t) {
        // todo
        return false;
    }

    /**
     * 并集另一个set的内容到当前set
     * @param another
     * @return 当前set
     */
    public FlowSet<T> union(FlowSet<T> another) {
        // todo
        return new FlowSet<>();
    }

    /**
     * 交集另一个集合到当前集合
     * @param another
     * @return 返回当前集合
     */
    public FlowSet<T> intersect(FlowSet<T> another) {
        // todo
        return new FlowSet<>();
    }

    /**
     * @return 返回当前集合集合的一个新创建的拷贝
     */
    public FlowSet<T> duplicate() {
        // todo
        return new FlowSet<>();
    }

    /**
     * 设置当前集合的内容与另一个集合内容一样
     * @param another
     * @return 返回当前集合
     */
    public FlowSet<T> setTo(FlowSet<T> another) {
        return new FlowSet<>();
    }
}
