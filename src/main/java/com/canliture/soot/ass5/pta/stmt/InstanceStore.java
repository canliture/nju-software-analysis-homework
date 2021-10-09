package com.canliture.soot.ass5.pta.stmt;

import com.canliture.soot.ass5.pta.elem.Field;
import com.canliture.soot.ass5.pta.elem.Variable;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:08 下午
 *
 * 实例字段写操作;
 * 如: x.f = y
 */
public class InstanceStore extends Statement {

    private Variable from;

    private Variable base;

    private Field field;

    public InstanceStore(Variable base, Field field, Variable from) {
        this.from = from;
        this.base = base;
        this.field = field;
    }

    /**
     * @return 赋值左侧字段的基变量(Base Variable)
     */
    public Variable getVariable() {
        return base;
    }

    /**
     * @return 赋值左侧的字段
     */
    public Field getField() {
        return field;
    }

    /**
     * @return 赋值右侧的变量
     */
    public Variable getFrom() {
        return from;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        InstanceStore store = (InstanceStore) o;
        return Objects.equals(from, store.from) && Objects.equals(base, store.base) && Objects.equals(field, store.field);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, base, field);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + base + "." + field + " = " + from;
    }
}
