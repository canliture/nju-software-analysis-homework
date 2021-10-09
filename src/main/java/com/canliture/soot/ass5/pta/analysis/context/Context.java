package com.canliture.soot.ass5.pta.analysis.context;

/**
 * Created by liture on 2021/10/10 12:07 上午
 *
 * 上下文
 *
 * also see:
 * @see DefaultContext
 * @see OneContext
 * @see TwoContext
 */
public interface Context {

    /**
     * @return 当前上下文的数量
     */
    int depth();

    /**
     * @param i
     * @return 当前上下文下第i个元素 (约定索引从1开始)
     */
    Object element(int i);
}
