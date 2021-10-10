package com.canliture.soot.ass5.pta.analysis.data;

import com.canliture.soot.ass5.pta.analysis.context.Context;
import com.canliture.soot.ass5.pta.elem.Method;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 12:41 上午
 *
 * 指针分析中上下文敏感的方法
 */
public class CSMethod extends ContextSensitive {

    private Method method;

    public CSMethod(Context context, Method method) {
        super(context);
        this.method = method;
    }

    /**
     * @return 具体的方法
     */
    public Method getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CSMethod csMethod = (CSMethod) o;
        return Objects.equals(method, csMethod.method);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), method);
    }

    @Override
    public String toString() {
        return super.toString() + ":" + method;
    }
}
