package com.canliture.soot.ass4.pta.analysis.ci;

import com.canliture.soot.ass4.pta.elem.Variable;

/**
 * Created by liture on 2021/9/20 11:10 下午
 *
 * PFG中的变量指针节点；本类的每个实例关联了一个变量
 *
 * @see PointerFlowGraph
 * @see Variable
 */
public class Var extends Pointer {

    public static Var getPointer(Variable variable) {
        // todo
        return null;
    }

    /**
     * @return 返回PFG上该指针节点对应的变量
     */
    public Variable getVariable() {
        // todo
        return null;
    }
}
