package com.canliture.soot.ass4.pta.elem;

import soot.jimple.AssignStmt;
import soot.tagkit.LineNumberTag;
import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:07 下午
 *
 * 抽象对象
 */
public class Obj {

    private Object allocSite;

    private String type;

    private Method method;

    public Obj(Object allocSite, String type, Method method) {
        this.allocSite = allocSite;
        this.type = type;
        this.method = method;
    }

    public Object getAllocSite() {
        return allocSite;
    }

    public String getType() {
        return type;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Obj obj = (Obj) o;
        return Objects.equals(allocSite, obj.allocSite)
                && Objects.equals(type, obj.type)
                && Objects.equals(method, obj.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(allocSite, type, method);
    }

    @Override
    public String toString() {
        AssignStmt assignStmt = (AssignStmt) allocSite;
        StringBuilder buff = new StringBuilder();
        buff.append(method.getSootMethod().getSignature());
        buff.append("@");
        buff.append(assignStmt.getTag(LineNumberTag.IDENTIFIER));
        buff.append(": ");
        buff.append(assignStmt);
        return buff.toString();
    }
}
