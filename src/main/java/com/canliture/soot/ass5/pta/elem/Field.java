package com.canliture.soot.ass5.pta.elem;

import soot.jimple.InstanceFieldRef;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:07 下午
 *
 * 字段
 */
public class Field {

    private final String fieldName;

    public Field(InstanceFieldRef f) {
        // 这里不实现严格检查类型了
        this(f.getField().getName());
    }

    public Field(String fieldName) {
        this.fieldName = fieldName;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Field field = (Field) o;
        return Objects.equals(fieldName, field.fieldName);
    }

    @Override
    public int hashCode() {
        return Objects.hash(fieldName);
    }

    @Override
    public String toString() {
        return fieldName;
    }
}
