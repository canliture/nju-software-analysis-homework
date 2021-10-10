package com.canliture.soot.ass5.pta.analysis.context;

import com.canliture.soot.ass5.pta.analysis.data.CSCallSite;
import com.canliture.soot.ass5.pta.analysis.data.CSMethod;
import com.canliture.soot.ass5.pta.analysis.data.CSObj;
import com.canliture.soot.ass5.pta.elem.Method;

/**
 * Created by liture on 2021/10/10 12:46 上午
 *
 * 上下文选择器接口, 他是具体上下文变种的接口
 *
 * also see:
 * @see ContextInsensitiveSelector
 * @see OneCallSelector // call-site context-sensitive
 * @see TwoCallSelector
 * @see OneObjectSelector // obj context-sensitive
 * @see TwoObjectSelector
 * @see OneTypeSelector // type context-sensitive
 * @see TwoTypeSelector
 */
public interface ContextSelector {

    /**
     * @return 对entry method的默认上下文，此接口不需要实现
     */
    default Context getDefaultContext() {
        return DefaultContext.v();
    }

    /**
     * 对静态方法选择上下文；
     * 当指针分析处理静态调用时，此接口被调用
     * @param csCallSite 上下文敏感的调用点
     * @param method 被调用函数
     * @return
     */
    Context selectContext(CSCallSite csCallSite, Method method);

    /**
     * 对实例方法(instance methods)选择上下文;
     * 当指针分析处理对象实例调用时，此接口被调用
     * @param csCallSite 上下文敏感的调用点
     * @param csObj 带有heap context的receiver object
     * @param method 被调用函数
     * @return
     */
    Context selectContext(CSCallSite csCallSite, CSObj csObj, Method method);

    /**
     * 对抽象对象选择堆上下文；
     * 当指针分析处理对象分配(object allocation)时，此接口被调用
     * @param csMethod 上下文敏感的方法，它包含了对象的分配点
     * @param o allocation site
     * @return
     */
    Context selectHeapContext(CSMethod csMethod, Object o);
}
