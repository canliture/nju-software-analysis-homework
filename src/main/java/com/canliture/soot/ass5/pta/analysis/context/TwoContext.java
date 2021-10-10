package com.canliture.soot.ass5.pta.analysis.context;

import java.util.Objects;

/**
 * Created by liture on 2021/10/10 12:11 上午
 */
public class TwoContext<T> implements Context {

    private T cxt1;

    private T ctx2;

    public TwoContext(T cxt1, T ctx2) {
        Objects.requireNonNull(cxt1);
        Objects.requireNonNull(ctx2);
        this.cxt1 = cxt1;
        this.ctx2 = ctx2;
    }

    @Override
    public int depth() {
        return 2;
    }

    @Override
    public T element(int i) {
        if (i == 1) {
            return cxt1;
        }
        if (i == 2) {
            return ctx2;
        }
        throw new IllegalArgumentException();
    }
}
