package com.canliture.soot.ass4.pta.analysis.ci;

import com.canliture.soot.ass3.JimpleCallGraph;
import com.canliture.soot.ass4.pta.analysis.HeapModel;
import com.canliture.soot.ass4.pta.elem.Method;
import com.canliture.soot.ass4.pta.elem.Obj;
import com.canliture.soot.ass4.pta.stmt.*;
import soot.toolkits.scalar.Pair;
import java.util.LinkedHashSet;
import java.util.Set;

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
     * 堆模型
     */
    private HeapModel heapModel;

    private Method entry;

    private WorkList WL;

    private PointerFlowGraph PFG;

    private Set<Statement> S;

    private Set<Method> RM;

    private JimpleCallGraph CG;

    public PointerAnalysis(Method entry) {
        this.entry = entry;
        this.heapModel = HeapModel.v();
    }

    /**
     * 开始指针分析算法
     */
    public void solve() {
        initialize();
        analysis();
    }

    /**
     * 实现指针分析算法(Lecture 10, Page 115)的前两行;
     * 初始化各种数据结构，entry methods
     */
    protected void initialize() {
        WL = new WorkList();
        PFG = new PointerFlowGraph();
        S = new LinkedHashSet<>();
        RM = new LinkedHashSet<>();
        CG = new JimpleCallGraph();
        addReachable(entry);
    }

    /**
     * 实现指针分析中的 WorkList 处理主循环
     */
    protected void analysis() {
        while (!WL.isEmpty()) {
            Pair<Pointer, PointsToSet> entry = WL.remove();

            // propagate
            Pointer n = entry.getO1();
            PointsToSet pts = entry.getO2();
            PointsToSet delta = propagate(n, pts);

            if (!(n instanceof Var)) {
                continue;
            }
            // now, n represents a variable x

            // foreach x.f = y
            processInstanceStore((Var) n, delta);
            // foreach y = x.f
            processInstanceLoad((Var) n, delta);
        }
    }

    protected void addReachable(Method m) {
        if (!RM.contains(m)) {
            RM.add(m);

            // S U= S_m
            Set<Statement> S_m = m.getStatements();
            S.addAll(S_m);

            // foreach i: x = new T() \in S_m
            processAllocations(m);

            // foreach x = y \in S_m
            processLocalAssign(m);
        }
    }

    /**
     * 这个方法实现集合的差运算: delta = pts - pts(n)
     * 另外还实现了Lecture 9, Page 43的 propagate(p, pts) 函数
     *
     * 这里合并了两个步骤到一个方法里面，用于降低冗余的计算
     * @param n
     * @param pts
     * @return pts - pts(n)
     */
    protected PointsToSet propagate(Pointer n, PointsToSet pts) {
        PointsToSet ptsOfn = PFG.getPts(n);
        PointsToSet delta = PointsToSet.difference(pts, ptsOfn);
        if (!delta.isEmpty()) {
            ptsOfn.union(delta);
            // foreach n -> s \in PFG
            for (Pointer s : PFG.getSuccessorOf(n)) {
                WL.addPointerEntry(s, delta);
            }
        }
        return delta;
    }

    /**
     * Lecture 9, Page 43 的 AddEdge 函数
     * @param s PFG边的source
     * @param t PFG边的destination
     */
    protected void addPFGEdge(Pointer s, Pointer t) {
        PFG.addEdge(s, t);
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
        Set<Statement> S_m = m.getStatements();
        S_m.stream()
            .filter(s -> s instanceof Allocation)
            .forEach(s -> {
                // i: x = new T()
                Allocation i = (Allocation) s;
                // o_i
                Obj o = heapModel.getObj(i.getAllocationSite(), i.getType(), m);
                // <x, o_i>
                WL.addPointerEntry(PFG.getVar(i.getVar()), PointsToSet.singleton(o));
            });
    }

    /**
     * 处理局部变量的赋值；
     * 如: x = y
     *
     * Lecture 10, Page 118 的 AddReachable函数的第二个foreach循环
     * @param m
     */
    protected void processLocalAssign(Method m) {
        Set<Statement> S_m = m.getStatements();
        S_m.stream()
            .filter(s -> s instanceof Assign)
            .forEach(s -> {
                // x = y
                Assign assign = (Assign) s;
                // y -> x
                addPFGEdge(PFG.getVar(assign.getFrom()), PFG.getVar(assign.getTo()));
            });
    }

    /**
     * 处理字段的写语句;
     * 如: x.f = y
     *
     * Lecture 10, Page 124 Solve函数中处理store语句的foreach循环
     * @param var pts改变的指针节点, 对应的指针变量为x
     * @param pts 改变的部分delta
     */
    protected void processInstanceStore(Var var, PointsToSet pts) {
        for (Obj o_i : pts) {
            S.stream()   // x.f = y
                .filter(s -> s instanceof InstanceStore
                        && ((InstanceStore) s).getVariable().equals(var.getVariable()))
                .forEach(s -> {
                    // x.f = y
                    InstanceStore store = (InstanceStore) s;
                    // y -> o_i.f
                    addPFGEdge(PFG.getVar(store.getFrom()), PFG.getInstanceField(o_i, store.getField()));
                });
        }
    }

    /**
     * 处理字段读语句;
     * 如: y = x.f
     *
     * Lecture 10, Page 124 Solve函数中处理load语句的foreach循环
     * @param var pts改变的指针节点, 对应的指针变量为x
     * @param pts 改变的部分delta
     */
    protected void processInstanceLoad(Var var, PointsToSet pts) {
        for (Obj o_i : pts) {
            S.stream()   // x = n.f
                .filter(s -> s instanceof InstanceLoad
                        && ((InstanceLoad) s).getBase().equals(var.getVariable()))
                .forEach(s -> {
                    // y = x.f
                    InstanceLoad load = (InstanceLoad) s;
                    // o_i.f -> y
                    addPFGEdge(PFG.getInstanceField(o_i, load.getField()), PFG.getVar(load.getTo()));
                });
        }
    }
}
