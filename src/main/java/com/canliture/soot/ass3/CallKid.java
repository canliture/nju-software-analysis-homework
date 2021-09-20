package com.canliture.soot.ass3;

import soot.Unit;

/**
 * Created by liture on 2021/9/20 1:49 下午
 */
public enum CallKid {

    INTERFACE("invokeinterface"),
    VIRTUAL("invokevirtual"),
    SPECIAL("invokespecial"),
    STATIC("invokestatic");

    private String inst;

    CallKid(String inst) {
        this.inst = inst;
    }

    /**
     * @param unit
     * @return 返回给定Unit的调用类型
     * @throws IllegalArgumentException 如果Unit不存在函数调用，那么抛出异常
     */
    public static CallKid getCallKind(Unit unit) throws IllegalArgumentException {
        // todo
        throw new IllegalArgumentException(unit.toString());
    }

    @Override
    public String toString() {
        return inst;
    }
}
