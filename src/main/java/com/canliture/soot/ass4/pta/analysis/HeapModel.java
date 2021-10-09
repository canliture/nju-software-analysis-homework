package com.canliture.soot.ass4.pta.analysis;

import com.canliture.soot.ass4.pta.elem.Method;
import com.canliture.soot.ass4.pta.elem.Obj;

/**
 * Created by liture on 2021/9/21 5:28 下午
 */
public class HeapModel {

    private static HeapModel instance;

    private HeapModel() {  }

    public static HeapModel v() {
        if (instance == null) {
            instance = new HeapModel();
        }
        return instance;
    }

    /**
     * @param allocSite 分配点的标识
     * @param type 默认为 allocation-site abstraction, 这里不会用到它的值
     * @param m 分配点所在的方法
     * @return 从当前堆模型中获取 抽象对象
     */
    public Obj getObj(Object allocSite, String type, Method m) {
        return new Obj(allocSite, type, m);
    }
}
