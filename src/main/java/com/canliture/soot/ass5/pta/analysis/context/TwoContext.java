package com.canliture.soot.ass5.pta.analysis.context;

/**
 * Created by liture on 2021/10/10 12:11 上午
 */
public class TwoContext<T> implements Context {

    public TwoContext(T cxt1, T ctx2) {
        // todo
    }

    @Override
    public int depth() {
        // todo
        return 0;
    }

    @Override
    public T element(int i) {
        throw new UnsupportedOperationException();
    }
}
