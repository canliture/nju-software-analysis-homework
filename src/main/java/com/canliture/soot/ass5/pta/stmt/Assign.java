package com.canliture.soot.ass5.pta.stmt;

import com.canliture.soot.ass5.pta.elem.Variable;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:08 下午
 *
 * 简单赋值; 如: x = y
 */
public class Assign extends Statement {

    private Variable from;

    private Variable to;

    public Assign(Variable from, Variable to) {
        this.from = from;
        this.to = to;
    }

    /**
     * @return 赋值右侧
     */
    public Variable getFrom() {
        return from;
    }

    /**
     * @return 赋值左侧
     */
    public Variable getTo() {
        return to;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Assign assign = (Assign) o;
        return Objects.equals(from, assign.from) && Objects.equals(to, assign.to);
    }

    @Override
    public int hashCode() {
        return Objects.hash(from, to);
    }

    @Override
    public String toString() {
        return super.toString() + ": " + from + " = " + to;
    }
}
