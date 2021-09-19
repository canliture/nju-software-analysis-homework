package com.canliture.soot.ass2;

import soot.Local;
import soot.Unit;
import soot.toolkits.graph.DirectedGraph;
import soot.toolkits.scalar.BackwardFlowAnalysis;

/**
 * Created by liture on 2021/9/19 9:55 下午
 */
public class LiveVariableAnalysis extends BackwardFlowAnalysis<Unit, FlowSet<Local>> {
    /**
     * Construct the analysis from a DirectedGraph representation of a Body.
     *
     * @param graph
     */
    public LiveVariableAnalysis(DirectedGraph<Unit> graph) {
        super(graph);
    }

    @Override
    protected void flowThrough(FlowSet<Local> in, Unit d, FlowSet<Local> out) {
        // todo
    }

    @Override
    protected FlowSet<Local> newInitialFlow() {
        // todo
        return new FlowSet<>();
    }

    @Override
    protected FlowSet<Local> entryInitialFlow() {
        // todo
        return new FlowSet<>();
    }

    @Override
    protected void merge(FlowSet<Local> in1, FlowSet<Local> in2, FlowSet<Local> out) {
        // todo
    }

    @Override
    protected void copy(FlowSet<Local> source, FlowSet<Local> dest) {
        // todo
    }

    @Override
    public void doAnalysis() {
        super.doAnalysis();
    }
}
