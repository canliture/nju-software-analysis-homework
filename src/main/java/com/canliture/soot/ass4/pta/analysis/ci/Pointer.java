package com.canliture.soot.ass4.pta.analysis.ci;

/**
 * Created by liture on 2021/9/20 11:09 下午
 *
 * 指针分析中的指针；Pointer Flow Graph中的节点
 * 每个指针表示一个 变量 或者一个 实例字段
 *
 * @see PointerFlowGraph
 * @see Var
 * @see InstanceField
 */
public class Pointer {

    /**
     * @return 指针的指向集合; 每个指针在创建时都关联了一个空集，所以不会返回null
     */
    public PointsToSet getPointsToSet() {
        // todo
        return new PointsToSet();
    }
}
