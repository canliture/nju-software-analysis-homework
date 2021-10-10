package com.canliture.soot.ass5.pta.analysis.data;

import com.canliture.soot.ass5.pta.analysis.context.Context;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 11:54 上午
 */
public abstract class ContextSensitive {

    private Context context;

    public ContextSensitive(Context context) {
        this.context = context;
    }

    /**
     * @return 修饰上下文
     */
    public Context getContext() {
        return context;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ContextSensitive that = (ContextSensitive) o;
        return Objects.equals(context, that.context);
    }

    @Override
    public int hashCode() {
        return Objects.hash(context);
    }

    @Override
    public String toString() {
        return context.toString();
    }
}
