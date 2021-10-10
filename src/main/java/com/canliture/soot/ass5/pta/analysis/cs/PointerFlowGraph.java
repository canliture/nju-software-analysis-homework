package com.canliture.soot.ass5.pta.analysis.cs;

import com.canliture.soot.ass5.pta.elem.Field;
import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.canliture.soot.ass5.pta.analysis.data.CSVariable;
import soot.toolkits.scalar.Pair;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.Map;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 11:11 下午
 *
 * 表示PFG，指针流图；
 * 它也维护了variable/instance fields到相关联指针节点(PFG节点)之间的映射
 */
public class PointerFlowGraph {

    protected Map<CSVariable, Var> varMap = new HashMap<>();

    protected Map<Pair<CSObj, Field>, InstanceField> instanceFieldMap = new HashMap<>();

    protected Map<Pointer, Set<Pointer>> node2Succes = new HashMap<>();

    /**
     * @param variable
     * @return 返回给定变量所关联的PFG上的Var指针节点
     */
    public Var getVar(CSVariable variable) {
        Var var;
        if ((var = varMap.get(variable)) == null) {
            var = new Var(variable);
            varMap.put(variable, var);
        }
        return var;
    }

    /**
     * @param obj
     * @param field
     * @return 返回给定对象+字段所关联的PFG上的InstanceField指针节点
     */
    public InstanceField getInstanceField(CSObj obj, Field field) {
        InstanceField ret;
        Pair<CSObj, Field> pair = new Pair<>(obj, field);
        if ((ret = instanceFieldMap.get(pair)) == null) {
            ret = new InstanceField(obj, field);
        }
        return ret;
    }

    /**
     * 添加一条 s -> t 的边到当前PFG中
     * @param s 边的source
     * @param s 变得destination
     * @return 如果边早已经存在，返回false；否则返回true
     */
    public boolean addEdge(Pointer s, Pointer t) {
        addIndex(s);
        addIndex(t);
        Set<Pointer> succes = node2Succes.computeIfAbsent(s, n -> new LinkedHashSet<>());
        return succes.add(t);
    }

    public void addIndex(Pointer p) {
        if (p instanceof Var) {
            Var var = (Var) p;
            CSVariable variable = var.getVariable();
            varMap.put(variable, var);
        } else if (p instanceof InstanceField) {
            InstanceField instanceField = (InstanceField) p;
            CSObj obj = instanceField.getBase();
            Field field = instanceField.getField();
            Pair<CSObj, Field> key = new Pair<>(obj, field);
            instanceFieldMap.put(key, instanceField);
        }
    }

    /**
     * @param pointer
     * @return 返回给定指针节点在PFG上的后继节点
     */
    public Set<Pointer> getSuccessorOf(Pointer pointer) {
        return node2Succes.computeIfAbsent(pointer, n -> new LinkedHashSet<>());
    }
}
