package com.canliture.soot.ass4.pta.stmt;

import com.canliture.soot.ass4.pta.elem.Variable;
import soot.jimple.Stmt;
import soot.tagkit.LineNumberTag;

import java.util.Objects;

/**
 * Created by liture on 2021/9/20 11:08 下午
 *
 * 内存分配语句;
 * 如: x = new T()
 */
public class Allocation extends Statement {

    private Variable var;

    private Object allocationSite;

    /**
     * @param var
     * @param allocationSite 默认为 {@link soot.jimple.Stmt}
     *                       且为 x = new T() 的形式;
     *                       如果传入的值为null, 那么它就被设为this
     */
    public Allocation(Variable var, Object allocationSite) {
        this.var = var;
        this.allocationSite = allocationSite;
        if (allocationSite == null) {
            this.allocationSite = this;
        }
    }

    /**
     * @return 获取内存分配赋值的左侧
     */
    public Variable getVar() {
        return var;
    }

    /**
     * @return 返回内存分配点的标识(抽象的唯一地址)
     */
    public Object getAllocationSite() {
        return allocationSite;
    }

    /**
     * 这里仅仅返回字符串，目前它没用到
     * @return
     */
    public String getType() {
        return "allocation-site abstraction";
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Allocation that = (Allocation) o;
        return Objects.equals(var, that.var) && Objects.equals(allocationSite, that.allocationSite);
    }

    @Override
    public int hashCode() {
        return Objects.hash(var, allocationSite);
    }

    @Override
    public String toString() {
        Stmt stmt = (Stmt) allocationSite;
        String superStr = super.toString();
        if (superStr.length() > 0) {
            return superStr + ": " + stmt.toString();
        }
        StringBuilder buff = new StringBuilder();
        buff.append(stmt.toString());
        buff.append("@");
        buff.append(stmt.getTag(LineNumberTag.IDENTIFIER));
        return buff.toString();
    }
}
