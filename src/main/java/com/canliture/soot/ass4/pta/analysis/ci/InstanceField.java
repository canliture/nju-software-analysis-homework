package com.canliture.soot.ass4.pta.analysis.ci;

import com.canliture.soot.ass4.pta.elem.Field;
import com.canliture.soot.ass4.pta.elem.Obj;

/**
 * Created by liture on 2021/9/20 11:10 下午
 *
 * 表示PFG中的实例字段; 本类的每个实例关联了一个实例字段
 *
 * @see PointerFlowGraph
 */
public class InstanceField extends Pointer {

    /**
     * @return 返回PFG上该指针节点对应的字段的基对象(Base Object)
     */
    public Obj getBase() {
        // todo
        return null;
    }

    /**
     * @return 返回PFG上该指针节点对应的字段
     */
    public Field getField() {
        // todo
        return null;
    }
}
