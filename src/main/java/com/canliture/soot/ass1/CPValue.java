package com.canliture.soot.ass1;

import java.util.Objects;

/**
 * Created by liture on 2021/9/19 1:19 下午
 *
 * Lattice Value for constant propagation
 */
public class CPValue {

    private final static CPValue NAC = new CPValue();
    private final static CPValue UNDEF = new CPValue();

    /**
     * concrete value
     */
    private int val;

    private CPValue() {
        throw new UnsupportedOperationException();
    }

    private CPValue(int val) {
        this.val = val;
    }

    public static CPValue getNAC() {
        return NAC;
    }

    public static CPValue getUndef() {
        return UNDEF;
    }

    public static CPValue makeConstant(int val) {
        return new CPValue(val);
    }

    /**
     * 格值的meet;
     * 你应该从 {@link FlowMap#meet(FlowMap, FlowMap)} 里面去调用
     * @param value1
     * @param value2
     * @return meet后的结果
     */
    public static CPValue meetValue(CPValue value1, CPValue value2) {
        // todo
        return UNDEF;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        CPValue cpValue = (CPValue) o;
        return val == cpValue.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }
}
