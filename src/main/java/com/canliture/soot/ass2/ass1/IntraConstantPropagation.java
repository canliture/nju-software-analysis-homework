package com.canliture.soot.ass2.ass1;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.jimple.AssignStmt;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

/**
 * Created by liture on 2021/9/19 1:08 下午
 *
 * Java语言的子集特性：
 * - 只考虑局部变量
 * - int类型的线性常量传播
 * - 考虑算数运算 +,-,*,/
 * - 考虑比较运算 ==, !=, >=, >, <=, <
 *
 * 只需要关注等式的右侧是
 * - Constant; 如: x = 1
 * - Variable; 如: x = y
 * - BinaryExpression; 如 x = a + b; x = 1 - c; x = 2 * 3; (另外还有比较运算)
 */
public class IntraConstantPropagation extends ForwardFlowAnalysis<Unit, FlowMap> {

    /**
     * Construct the analysis from a DirectedGraph representation of a Body.
     *
     * @param graph
     */
    public IntraConstantPropagation(DirectedGraph<Unit> graph) {
        super(graph);
    }

    /**
     * 对应到ass1里面的 boolean transfer(Unit,FlowMap,FlowMap)
     * @param in 输入数据流
     * @param d 经过的Transfer语句节点
     * @param out
     */
    @Override
    protected void flowThrough(FlowMap in, Unit d, FlowMap out) {
        copy(in, out);
        if (d instanceof AssignStmt) {
            AssignStmt ass = (AssignStmt) d;
            Value lVal = ass.getLeftOp();
            if (lVal instanceof Local) {
                Local definedLocal = (Local) lVal;

                // 计算右侧的格值
                Value rightVal = ass.getRightOp();
                CPValue rightCPValue = in.computeValue(rightVal);

                out.put(definedLocal, rightCPValue);
            }
        }
    }

    @Override
    protected FlowMap newInitialFlow() {
        return new FlowMap();
    }

    @Override
    protected void merge(FlowMap in1, FlowMap in2, FlowMap out) {
        FlowMap meet = FlowMap.meet(in1, in2);
        copy(meet, out);
    }

    @Override
    protected void copy(FlowMap source, FlowMap dest) {
        dest.copyFrom(source);
    }

    @Override
    public void doAnalysis() {
        super.doAnalysis();
    }
}
