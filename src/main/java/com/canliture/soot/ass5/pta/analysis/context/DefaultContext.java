package com.canliture.soot.ass5.pta.analysis.context;

/**
 * Created by liture on 2021/10/10 12:10 上午
 *
 * 默认/空上下文
 * 用做entry method的上下文
 */
public class DefaultContext implements Context {

    private static DefaultContext INSTANCE;

    public static DefaultContext v() {
        if (INSTANCE == null) {
            INSTANCE = new DefaultContext();
        }
        return INSTANCE;
    }

    private DefaultContext() {  }

    @Override
    public int depth() {
        return 0;
    }

    @Override
    public Object element(int i) {
        throw new IllegalStateException();
    }

    @Override
    public String toString() {
        return "[]";
    }
}
