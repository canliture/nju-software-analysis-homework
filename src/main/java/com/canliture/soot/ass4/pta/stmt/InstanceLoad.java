package com.canliture.soot.ass4.pta.stmt;

import com.canliture.soot.ass4.pta.elem.Field;
import com.canliture.soot.ass4.pta.elem.Variable;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:09 下午
 *
 * 字段读操作;
 * 如: y = x.f
 */
public class InstanceLoad extends Statement {

    private final Variable base;

    private final Field field;

    private final Variable to;

    public InstanceLoad(Variable to, Variable base, Field field) {
        this.base = base;
        this.field = field;
        this.to = to;
    }

    /**
     * @return 读字段后写入的变量
     */
    public Variable getTo() {
        return to;
    }

    /**
     * @return 被读字段的基变量(Base Variable)
     */
    public Variable getBase() {
        return base;
    }

    /**
     * @return 被读字段
     */
    public Field getField() {
        return field;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceLoad that = (InstanceLoad) o;
        return Objects.equals(base, that.base) && Objects.equals(field, that.field) && Objects.equals(to, that.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(base, field, to);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + to + " = " + base + "." + field;
    }
}
