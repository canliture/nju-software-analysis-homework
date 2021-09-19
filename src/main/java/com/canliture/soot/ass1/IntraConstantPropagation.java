package com.canliture.soot.ass1;

import soot.Unit;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.ForwardFlowAnalysis;

/**
 * Created by liture on 2021/9/19 1:08 下午
 *
 * Java语言的子集特性：
 * - 只考虑局部变量
 * - int类型的线性常量传播
 * - 考虑算数运算 +,-,*,/
 *
 * 只需要关注等式的右侧是
 * - Constant; 如: x = 1
 * - Variable; 如: x = y
 * - BinaryExpression; 如 x = a + b; x = 1 - c; x = 2 * 3
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

    }

    @Override
    protected FlowMap newInitialFlow() {
        return null;
    }

    @Override
    protected void merge(FlowMap in1, FlowMap in2, FlowMap out) {

    }

    @Override
    protected void copy(FlowMap source, FlowMap dest) {

    }
}
