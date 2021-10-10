package com.canliture.soot.ass5.pta.analysis.cs;

import com.canliture.soot.ass5.pta.analysis.data.CSVariable;
import com.canliture.soot.ass5.pta.elem.Variable;
import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:10 下午
 *
 * PFG中的变量指针节点；本类的每个实例关联了一个变量
 *
 * @see PointerFlowGraph
 * @see Variable
 */
public class Var extends Pointer {

    private CSVariable var;

    public Var(CSVariable var) {
        this.var = var;
    }

    /**
     * @return 返回PFG上该指针节点对应的变量
     */
    public CSVariable getVariable() {
        return var;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Var var1 = (Var) o;
        return Objects.equals(var, var1.var);
    }

    @Override
    public int hashCode() {
        return Objects.hash(var);
    }

    @Override
    public String toString() {
        // fixme
        return "";
    }
}
