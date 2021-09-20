package com.canliture.soot.ass2.ass1;

import java.util.Objects;

/**
 * Created by liture on 2021/9/19 1:19 下午
 *
 * Lattice Value for constant propagation
 */
public class CPValue {

    private final static CPValue NAC = new CPValue() {
        @Override
        public int hashCode() {
            return Integer.MIN_VALUE;
        }
    };

    private final static CPValue UNDEF = new CPValue() {
        @Override
        public int hashCode() {
            return Integer.MAX_VALUE;
        }
    };

    /**
     * concrete value (整型，或者特殊地：0表示false，1表示true)
     */
    private int val;

    private CPValue() { }

    private CPValue(int val) {
        this.val = val;
    }

    public int val() {
        return val;
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

    public static CPValue makeConstant(boolean val) {
        return val ? makeConstant(1) : makeConstant(0);
    }

    /**
     * 格值的meet;
     * 你应该从 {@link FlowMap#meet(FlowMap, FlowMap)} 里面去调用
     * @param value1
     * @param value2
     * @return meet后的结果
     */
    public static CPValue meetValue(CPValue value1, CPValue value2) {
        if (value1 == getUndef()) {
            return value2;
        }

        if (value2 == getUndef()) {
            return value1;
        }

        if (value1 == getNAC() || value2 == getNAC()) {
            return getNAC();
        }

        // 两个具体的值meet
        if (value1.val() == value2.val()) {
            return makeConstant(value1.val());
        }

        return getNAC();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (this == UNDEF && o == NAC) return false;
        if (this == NAC && o == UNDEF) return false;
        CPValue cpValue = (CPValue) o;
        return val == cpValue.val;
    }

    @Override
    public int hashCode() {
        return Objects.hash(val);
    }

    @Override
    public String toString() {
        if (this == NAC) {
            return "NAC";
        }
        if (this == UNDEF) {
            return "UNDEF";
        }
        return String.valueOf(val);
    }
}
