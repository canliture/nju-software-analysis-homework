package com.canliture.soot.ass4.pta.stmt;

import com.canliture.soot.ass4.pta.elem.Method;

/**
 * Created by liture on 2021/9/20 11:17 下午
 *
 * 指针相关操作(Pointer-affecting)的语句
 *
 * @see Allocation
 * @see Assign
 * @see InstanceStore
 * @see InstanceLoad
 * @see Call
 */
public class Statement {

    private Method enclosingMethod;

    private int line = -1;

    public void setEnclosingMethod(Method method) {
        this.enclosingMethod = method;
    }

    public void setLine(int line) {
        this.line = line;
    }

    @Override
    public String toString() {
        StringBuilder buff = new StringBuilder();
        if (enclosingMethod != null) {
            buff.append(enclosingMethod.getSootMethod().getSignature());
        }
        if (line >= 0) {
            buff.append("@").append(line);
        }
        return buff.toString();
    }
}
