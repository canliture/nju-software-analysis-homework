package com.canliture.soot.ass4.pta.analysis.ci;

import com.canliture.soot.ass4.pta.elem.Method;

/**
 * Created by liture on 2021/9/20 11:11 下午
 *
 * 本类实现了指针分析算法(对应Lecture 10的115页)
 *
 * assignment4说:
 * 它已经实现了处理方法调用的路基了，我们不需要管了？(这个尝试自己实现吧...)
 */
public class PointerAnalysis {

    /**
     * 开始指针分析算法
     */
    public void solve() {
        // todo
    }

    /**
     * 实现指针分析算法(Lecture 10, Page 115)的前两行;
     * 初始化各种数据结构，entry methods
     */
    public void initialize() {
        // todo
    }

    /**
     * 实现指针分析中的 WorkList 处理主循环
     */
    public void analysis() {
        // todo
    }

    /**
     * 这个方法实现集合的差运算: delta = pts - pts(n)
     * 另外还实现了Lecture 9, Page 43的 propagate(p, pts) 函数
     *
     * 这里合并了两个步骤到一个方法里面，用于降低冗余的计算
     * @param p
     * @param pts
     * @return
     */
    protected PointsToSet propagate(Pointer p, PointsToSet pts) {
        // todo
        return new PointsToSet();
    }

    /**
     * Lecture 9, Page 43 的 AddEdge 函数
     * @param s PFG边的source
     * @param t PFG边的destination
     */
    protected void addPFGEdge(Pointer s, Pointer t) {
        // todo
    }

    /**
     * 处理新的可达方法内的allocations；
     * Lecture 10, Page 118 的 AddReachable函数的第一个foreach循环
     *
     * 提示：
     * 需要实现堆模型(heap abstraction)来获取抽象对象(abstract object).
     * 我们使用allocation-size abstraction，以至于每个allocation site生成一个abstract object
     *
     * assignment4说:
     * 我们能够使用当前类的heapModel字段来获取堆模型上的抽象对象:
     *
     * Allocation alloc = ...;
     * Object allocSite = alloc.getAllocationSite();
     * Obj obj = heapModel.getObj(allocSite, alloc.getType(), method);
     *
     * 这个heapModel尝试自己实现吧...
     * @param m
     */
    protected void processAllocations(Method m) {
        // todo
    }

    /**
     * 处理局部变量的赋值；
     * 如: x = y
     *
     * Lecture 10, Page 118 的 AddReachable函数的第二个foreach循环
     * @param m
     */
    protected void processLocalAssign(Method m) {
        // todo
    }

    /**
     * 处理字段的写语句;
     * 如: x.f = y
     *
     * Lecture 10, Page 124 Solve函数中处理store语句的foreach循环
     * @param var pts改变的指针节点
     * @param pts 改变的部分delta
     */
    protected void processInstanceStore(Var var, PointsToSet pts) {
        // todo
    }

    /**
     * 处理字段读语句;
     * 如: y = x.f
     *
     * Lecture 10, Page 124 Solve函数中处理load语句的foreach循环
     * @param var pts改变的指针节点
     * @param pts 改变的部分delta
     */
    protected void processInstanceLoad(Var var, PointsToSet pts) {
        // todo
    }
}
