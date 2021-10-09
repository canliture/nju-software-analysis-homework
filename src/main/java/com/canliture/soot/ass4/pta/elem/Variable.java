package com.canliture.soot.ass4.pta.elem;

import com.canliture.soot.ass4.pta.stmt.Call;
import com.canliture.soot.ass4.pta.stmt.InstanceLoad;
import com.canliture.soot.ass4.pta.stmt.InstanceStore;
import soot.Local;
import java.util.*;

/**
 * Created by liture on 2021/9/20 11:06 下午
 *
 * 表示local variables
 */
public class Variable {

    private final Local local;

    private final Method method;

    private final Set<InstanceStore> stores;

    private final Set<InstanceLoad> loads;

    private final Set<Call> calls;

    public Variable(Local local, Method method) {
        this.local = local;
        this.method = method;
        this.stores = new LinkedHashSet<>();
        this.loads = new LinkedHashSet<>();
        this.calls = new LinkedHashSet<>();
    }

    /**
     * @return 返回函数内对当前变量的store语句
     */
    public Set<InstanceStore> getStores() {
        return stores;
    }

    /**
     * @return 返回函数内对当前变量的load语句
     */
    public Set<InstanceLoad> getLoads() {
        return loads;
    }

    public Set<Call> getCalls() {
        return calls;
    }

    public Method getMethod() {
        return method;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Variable variable = (Variable) o;
        return Objects.equals(local, variable.local);
    }

    @Override
    public int hashCode() {
        return Objects.hash(local);
    }

    @Override
    public String toString() {
        return toUniqueString();
    }

    public String toUniqueString() {
        return method.getSootMethod().getSignature() + ": " + local.getName();
    }
}
