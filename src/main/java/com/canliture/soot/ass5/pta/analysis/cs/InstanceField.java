package com.canliture.soot.ass5.pta.analysis.cs;

import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.canliture.soot.ass5.pta.elem.Field;
import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:10 下午
 *
 * 表示PFG中的实例字段; 本类的每个实例关联了一个实例字段
 *
 * @see PointerFlowGraph
 */
public class InstanceField extends Pointer {

    private CSObj o;

    private Field f;

    public InstanceField(CSObj o, Field f) {
        this.o = o;
        this.f = f;
    }

    /**
     * @return 返回PFG上该指针节点对应的字段的基对象(Base Object)
     */
    public CSObj getBase() {
        return o;
    }

    /**
     * @return 返回PFG上该指针节点对应的字段
     */
    public Field getField() {
        return f;
    }

    @Override
    public boolean equals(Object o1) {
        if (this == o1) return true;
        if (o1 == null || getClass() != o1.getClass()) return false;
        InstanceField that = (InstanceField) o1;
        return Objects.equals(o, that.o) && Objects.equals(f, that.f);
    }

    @Override
    public int hashCode() {
        return Objects.hash(o, f);
    }

    @Override
    public String toString() {
        return "(" + o + ")" + "." + f;
    }
}
