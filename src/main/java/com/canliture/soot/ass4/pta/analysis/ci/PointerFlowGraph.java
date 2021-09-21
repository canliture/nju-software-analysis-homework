package com.canliture.soot.ass4.pta.analysis.ci;

import com.canliture.soot.ass4.pta.elem.Field;
import com.canliture.soot.ass4.pta.elem.Obj;
import com.canliture.soot.ass4.pta.elem.Variable;

import java.util.Collections;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 11:11 下午
 *
 * 表示PFG，指针流图；
 * 它也维护了variable/instance fields到相关联指针节点(PFG节点)之间的映射
 */
public class PointerFlowGraph {

    /**
     * @param variable
     * @return 返回给定变量所关联的PFG上的Var指针节点
     */
    public Var getVar(Variable variable) {
        // todo
        return null;
    }

    /**
     * @param obj
     * @param field
     * @return 返回给定对象+字段所关联的PFG上的InstanceField指针节点
     */
    public InstanceField getInstanceField(Obj obj, Field field) {
        // todo
        return null;
    }

    /**
     * 添加一条 s -> t 的边到当前PFG中
     * @param s 边的source
     * @param s 变得destination
     * @return 如果边早已经存在，返回false；否则返回true
     */
    public boolean addEdge(Pointer s, Pointer t) {
        // todo
        return false;
    }

    /**
     * @param pointer
     * @return 返回给定指针节点在PFG上的后继节点
     */
    public Set<Pointer> getSuccessorOf(Pointer pointer) {
        // todo
        return Collections.emptySet();
    }
}
