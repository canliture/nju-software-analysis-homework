package com.canliture.soot.ass4.pta.elem;

import com.canliture.soot.ass4.pta.stmt.InstanceLoad;
import com.canliture.soot.ass4.pta.stmt.InstanceStore;

import java.util.Collections;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 11:06 下午
 *
 * 表示local variables
 */
public class Variable {

    /**
     * @return 返回函数内对当前变量的store语句
     */
    public Set<InstanceStore> getStores() {
        // todo
        return Collections.emptySet();
    }

    /**
     * @return 返回函数内对当前变量的load语句
     */
    public Set<InstanceLoad> getLoads() {
        // todo
        return Collections.emptySet();
    }
}
