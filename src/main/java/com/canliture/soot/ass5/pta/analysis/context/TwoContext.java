package com.canliture.soot.ass5.pta.analysis.context;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 12:11 上午
 */
public class TwoContext<T> implements Context {

    private final T ctx1;

    private final T ctx2;

    public TwoContext(T ctx1, T ctx2) {
        Objects.requireNonNull(ctx1);
        this.ctx1 = ctx1;
        this.ctx2 = ctx2;
    }

    @Override
    public int depth() {
        if (ctx2 == null) {
            return 1;
        }
        return 2;
    }

    @Override
    public T element(int i) {
        if (i == 1) {
            return ctx1;
        }
        if (i == 2) {
            return ctx2;
        }
        throw new IllegalArgumentException();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TwoContext<?> that = (TwoContext<?>) o;
        return Objects.equals(ctx1, that.ctx1) && Objects.equals(ctx2, that.ctx2);
    }

    @Override
    public int hashCode() {
        return Objects.hash(ctx1, ctx2);
    }

    @Override
    public String toString() {
        if (depth() == 1) {
            return "[" + ctx1 + ", ]";
        }
        if (depth() == 2) {
            return "[" + ctx1 + ", " + ctx2 + "]";
        }
        throw new IllegalStateException();
    }
}
