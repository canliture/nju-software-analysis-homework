package com.canliture.soot.ass5.pta.analysis.data;

import com.canliture.soot.ass5.pta.elem.Obj;
import com.canliture.soot.ass5.pta.analysis.context.Context;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 12:44 上午
 *
 * 指针分析中上下文敏感的对象
 */
public class CSObj extends ContextSensitive {

    private Obj obj;

    public CSObj(Context context, Obj obj) {
        super(context);
        this.obj = obj;
    }

    /**
     * @return 具体的对象
     */
    public Obj getObject() {
        return obj;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        CSObj csObj = (CSObj) o;
        return Objects.equals(obj, csObj.obj);
    }

    @Override
    public int hashCode() {
        return Objects.hash(super.hashCode(), obj);
    }

    @Override
    public String toString() {
        return super.toString() + ":" + obj;
    }
}
