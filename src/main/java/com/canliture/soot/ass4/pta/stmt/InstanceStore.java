package com.canliture.soot.ass4.pta.stmt;

import com.canliture.soot.ass4.pta.elem.Field;
import com.canliture.soot.ass4.pta.elem.Variable;

/**
 * Created by liture on 2021/9/20 11:08 下午
 *
 * 实例字段写操作;
 * 如: x.f = y
 */
public class InstanceStore extends Statement {

    /**
     * @return 赋值左侧字段的基变量(Base Variable)
     */
    public Variable getVariable() {
        // todo
        return null;
    }

    /**
     * @return 赋值左侧的字段
     */
    public Field getField() {
        // todo
        return null;
    }

    /**
     * @return 赋值右侧的变量
     */
    public Variable getFrom() {
        // todo
        return null;
    }
}
