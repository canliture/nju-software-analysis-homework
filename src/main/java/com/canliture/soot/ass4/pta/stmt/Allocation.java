package com.canliture.soot.ass4.pta.stmt;

import com.canliture.soot.ass4.pta.elem.Variable;

/**
 * Created by liture on 2021/9/20 11:08 下午
 *
 * 内存分配语句;
 * 如: x = new T()
 */
public class Allocation extends Statement {

    /**
     * @return 获取内存分配赋值的左侧
     */
    public Variable getVar() {
        // todo
        return null;
    }

    /**
     * @return 返回内存分配点的标识(抽象的唯一地址)
     */
    public Object getAllocationSite() {
        // todo
        return null;
    }
}
