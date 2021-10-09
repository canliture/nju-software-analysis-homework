package com.canliture.soot.ass5.pta.elem;

import soot.SootClass;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 12:31 上午
 *
 * 表示types，也是上下文Type Sensitive的用于修饰数据的上下文元素
 */
public class Type {

    private SootClass sootClass;

    public Type(SootClass sootClass) {
        this.sootClass = sootClass;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Type type = (Type) o;
        return Objects.equals(sootClass, type.sootClass);
    }

    @Override
    public int hashCode() {
        return Objects.hash(sootClass);
    }
}
