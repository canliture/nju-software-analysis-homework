package com.canliture.soot.ass4.pta.stmt;

import com.canliture.soot.ass4.pta.elem.Field;
import com.canliture.soot.ass4.pta.elem.Variable;

/**
 * Created by liture on 2021/9/20 11:09 下午
 *
 * 字段读操作;
 * 如: y = x.f
 */
public class InstanceLoad extends Statement {

    /**
     * @return 读字段后写入的变量
     */
    public Variable getTo() {
        // todo
        return null;
    }

    /**
     * @return 被读字段的基变量(Base Variable)
     */
    public Variable getBase() {
        // todo
        return null;
    }

    /**
     * @return 被读字段
     */
    public Field getField() {
        // todo
        return null;
    }
}
