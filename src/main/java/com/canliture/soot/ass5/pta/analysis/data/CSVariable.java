package com.canliture.soot.ass5.pta.analysis.data;

import com.canliture.soot.ass5.pta.analysis.context.Context;
import com.canliture.soot.ass5.pta.elem.Variable;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 1:48 上午
 */
public class CSVariable extends ContextSensitive {

    private Variable variable;

    public CSVariable(Context context, Variable variable) {
        super(context);
        this.variable = variable;
    }

    public Variable getVariable() {
        return variable;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CSVariable that = (CSVariable) o;
        return Objects.equals(variable, that.variable);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), variable);
    }

    @Override
    public String toString() {
        return super.toString() + ":" + variable;
    }
}
