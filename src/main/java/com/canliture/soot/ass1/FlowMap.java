package com.canliture.soot.ass1;

import soot.Local;
import soot.Value;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Created by liture on 2021/9/19 1:32 下午
 */
public class FlowMap {

    Map<Local, CPValue> delegateMap;

    public FlowMap() {
        this(new HashMap<>());
    }

    public FlowMap(Map<Local, CPValue> map) {
        this.delegateMap = map;
    }

    /**
     * 获取Local所关联的值
     * @param local soot中的局部变量
     * @return 如果存在关联的值, 返回该值；否则返回 {@link CPValue#getUndef()}
     */
    public CPValue get(Local local) {
        // todo
        return null;
    }

    /**
     * 关联Local给定的格值
     * @param local soot中的局部变量
     * @param value 常量传播中的格值
     * @return 旧的值
     */
    public CPValue put(Local local, CPValue value) {
        // todo
        return null;
    }

    /**
     * 更新局部变量的格值
     * @param local
     * @param value
     * @return
     */
    public boolean update(Local local, CPValue value) {
        // todo
        return false;
    }

    /**
     * @return map中所有局部变量
     */
    public Set<Local> keySet() {
        // todo
        return Collections.emptySet();
    }

    /**
     * 从给定map拷贝内容到当前map
     * @param flowMap 给定另一个map
     * @return 拷贝操作改变当前map的内容，则返回true
     */
    public boolean copyFrom(FlowMap flowMap) {
        return false;
    }

    /**
     * 给定赋值右侧的soot Value; 计算它的格值
     * @param sootValue 例如Local，BinaryExpression，IntConstant等
     * @return 计算得到的格值
     */
    public CPValue computeValue(Value sootValue) {
        // todo
        return CPValue.getUndef();
    }

    // - - - - - - - - - static methods

    /**
     * 常量传播的Meet function：meet两个FlowMap并返回meet的结果
     * 用于处理控制流交汇
     * @param map1
     * @param map2
     * @return meet的结果Map
     */
    public static FlowMap meet(FlowMap map1, FlowMap map2) {
        // todo
        return new FlowMap();
    }
}
