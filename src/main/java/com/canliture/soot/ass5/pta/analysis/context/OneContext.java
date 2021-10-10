package com.canliture.soot.ass5.pta.analysis.context;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 12:11 上午
 */
public class OneContext<T> implements Context {

    private final T ctx;

    public OneContext(T ctx) {
        Objects.requireNonNull(ctx);
        this.ctx = ctx;
    }

    @Override
    public int depth() {
        return 1;
    }

    @Override
    public T element(int i) {
        if (i == 1) {
            return ctx;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        OneContext<?> that = (OneContext<?>) o;
        return Objects.equals(ctx, that.ctx);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctx);
    }

    @Override
    public String toString() {
        return "[" + ctx + "]";
    }
}
