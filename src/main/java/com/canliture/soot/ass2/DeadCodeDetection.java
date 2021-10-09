package com.canliture.soot.ass2;

import com.canliture.soot.ass2.ass1.CPValue;
import com.canliture.soot.ass2.ass1.FlowMap;
import com.canliture.soot.ass2.ass1.IntraConstantPropagation;
import soot.*;
import soot.jimple.*;
import soot.toolkits.graph.BriefUnitGraph;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.graph.UnitGraph;

import java.util.*;

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

        // 目前在原body的拷贝上进行操作
        b = (Body) b.clone();

        Set<Unit> result = new HashSet<>();
        int prevSize = -1;
        while (prevSize != result.size()) {
            prevSize = result.size();

            // 找到Dead Assignment
            Set<Unit> deadAssignments = findDeadAssignments(b);

            // 创建cfg
            UnitGraph cfg = new BriefUnitGraph(b);

            // 利用常量传播找到不可达分支
            EdgeSet unreachableBranchEdge = findUnreachableBranches(b, cfg);

            // 找到不可达代码
            Set<Unit> unreachableCode = findUnreachableCode(cfg, unreachableBranchEdge);

            // 添加到不可达代码集合中
            result.addAll(deadAssignments);
            result.addAll(unreachableCode);
            result.removeIf(unit -> (unit instanceof IdentityUnit)
                                 || (unit instanceof ReturnStmt)
                                 || (unit instanceof RetStmt)
                                 || (unit instanceof ReturnVoidStmt));

            // 删除dead code或者不可达代码
            System.out.println(String.format("- - - - - Dead Code Of Method %s - - - - -", b.getMethod().getName()));
            b.getUnits().removeIf(unit -> {
                boolean ret = result.contains(unit);
                if (ret) {
                    System.out.println(unit);
                }
                return ret;
            });
            System.out.println(String.format("- - - - - End of Dead Code Of Method %s - - - - -\n\n", b.getMethod().getName()));
        }

        System.out.println(String.format("- - - - - After Optimization of Method %s - - - - -", b.getMethod().getName()));
        Body body = (Body) b.clone();
        body.getUnits().removeIf(result::contains);
        System.out.println(body);
        System.out.println(String.format("- - - - - Enf of After Optimization Of Method %s - - - - -", b.getMethod().getName()));

        return result;
    }

    /**
     * @param cfg
     * @return 返回给定cfg的entry，我们需要这个entry来对cfg进行遍历
     */
    public Unit getEntry(DirectedGraph<Unit> cfg) {
        return cfg.getHeads().get(0);
    }

    /**
     * 使用本方法来检测死赋值(dead assignment)
     * 如果有副作用，则不认为它可以被remove优化掉，即时赋值的左侧是not live的
     * @param stmt
     * @return 是否一个赋值可能有副作用(side-effect)
     */
    public static boolean mayHaveSideEffect(AssignStmt stmt) {
        return stmt.containsInvokeExpr();
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
        // 执行常量传播，用于收集条件恒为true/false的不可达分支
        IntraConstantPropagation constantPropagation = new IntraConstantPropagation(cfg);
        constantPropagation.doAnalysis();

        EdgeSet unreachableBranches = new EdgeSet();
        for (Unit unit : body.getUnits()) {
            if (unit instanceof IfStmt) {
                IfStmt ifStmt = (IfStmt) unit;
                Value v = ifStmt.getCondition();

                // 获取IfStmt之前的数据流，计算v的值
                FlowMap dataflow = constantPropagation.getFlowBefore(ifStmt);
                CPValue cpValue = dataflow.computeValue(v);

                // 是具体的值
                if (cpValue != CPValue.getUndef() && cpValue != CPValue.getNAC()) {
                    if (cpValue.val() == 0) {
                        // 恒为true, 那么true分支不可达
                        unreachableBranches.addEdge(ifStmt, ifStmt.getTarget());
                    } else if (cpValue.val() == 1) {
                        // 恒为false, 那么false分支不可达
                        unreachableBranches.addEdge(ifStmt, body.getUnits().getSuccOf(ifStmt));
                    }
                }
            }
        }

        return unreachableBranches;
    }

    /**
     * 从entry节点开始遍历给定的cfg, 遍历的过程中，应该跳过给定的 unreachableEdgeSet 中不可达的分支
     * @param cfg
     * @param unreachableEdgeSet
     * @return
     */
    private Set<Unit> findUnreachableCode(DirectedGraph<Unit> cfg, EdgeSet unreachableEdgeSet) {
        // 最终求解的不可达代码
        Set<Unit> unreachableUnits = new HashSet<>();

        Set<Unit> visited = new HashSet<>();
        Queue<Unit> q = new LinkedList<>();
        q.add(getEntry(cfg));
        while (!q.isEmpty()) {
            Unit curr = q.poll();

            // 如果已经访问过了，直接返回
            if (visited.contains(curr)) {
                continue;
            }
            // 对当前Unit做标记，表示已经访问过
            visited.add(curr);

            // 将可达后继入队列
            List<Unit> succs = cfg.getSuccsOf(curr);
            for (Unit succ : succs) {
                if (!unreachableEdgeSet.containsEdge(curr, succ)) {
                    q.add(succ);
                }
            }
        }

        // 未被访问过的Unit，为unreachable code
        for (Unit unit : cfg) {
            if (!visited.contains(unit)) {
                unreachableUnits.add(unit);
            }
        }

        return unreachableUnits;
    }

    private Set<Unit> findDeadAssignments(Body body) {
        // 执行活性分析，用于删除dead assignment；注意side-effect不能删除
        BriefUnitGraph cfg = new BriefUnitGraph(body);
        LiveVariableAnalysis liveVariableAnalysis = new LiveVariableAnalysis(cfg);
        liveVariableAnalysis.doAnalysis();

        System.out.print("============================= ");
        System.out.println(String.format("Liveness of method %s", body.getMethod().getSignature()));
        Set<Unit> deadAssignments = new HashSet<>();
        for (Unit unit : cfg) {
            System.out.println(String.format("Before %s: %s", unit, liveVariableAnalysis.getFlowBefore(unit)));
            System.out.println(String.format("After %s: %s", unit, liveVariableAnalysis.getFlowAfter(unit)));
            // 如果是赋值语句，判断是否是 dead assignment
            if (unit instanceof AssignStmt) {
                AssignStmt assign = (AssignStmt) unit;
                Value v = assign.getLeftOp();
                if (v instanceof Local) {
                    Local local = (Local) v;
                    FlowSet<Local> liveSet = liveVariableAnalysis.getFlowAfter(assign);
                    // 不活跃 & 没有副作用
                    if (!liveSet.contains(local) && !mayHaveSideEffect(assign)) {
                        // 不可达代码
                        deadAssignments.add(unit);
                    }
                }
            }
        }
        System.out.print("============================= ");
        System.out.println(String.format("End of Liveness of method %s", body.getMethod().getSignature()));
        return deadAssignments;
    }
}
