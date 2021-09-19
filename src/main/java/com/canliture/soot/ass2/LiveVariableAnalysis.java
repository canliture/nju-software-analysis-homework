package com.canliture.soot.ass2;

import soot.Local;
import soot.Unit;
import soot.Value;
import soot.ValueBox;
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
        copy(in, out);
        kill(d, out);
        gen(d, out);
    }

    private void kill(Unit d, FlowSet<Local> out) {
        for (ValueBox defBox : d.getDefBoxes()) {
            Value v = defBox.getValue();
            if (v instanceof Local) {
                out.remove((Local) v);
            }
        }
    }

    private void gen(Unit d, FlowSet<Local> out) {
        for (ValueBox useBox : d.getUseBoxes()) {
            Value v = useBox.getValue();
            if (v instanceof Local) {
                out.add((Local) v);
            }
        }
    }

    @Override
    protected FlowSet<Local> newInitialFlow() {
        return new FlowSet<>();
    }

    @Override
    protected FlowSet<Local> entryInitialFlow() {
        return new FlowSet<>();
    }

    @Override
    protected void merge(FlowSet<Local> in1, FlowSet<Local> in2, FlowSet<Local> out) {
        FlowSet<Local> source = in1.duplicate();
        source.union(in2);
        copy(source, out);
    }

    @Override
    protected void copy(FlowSet<Local> source, FlowSet<Local> dest) {
        dest.setTo(source);
    }

    @Override
    public void doAnalysis() {
        super.doAnalysis();
    }
}
