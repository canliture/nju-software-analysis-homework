package com.canliture.soot.ass2;

import com.canliture.soot.ass2.ass1.CPValue;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Set;

/**
 * Created by liture on 2021/9/20 11:46 上午
 */
public class HashTest {

    @Test
    public void test() {
        CPValue max = CPValue.makeConstant(Integer.MAX_VALUE);
        CPValue min = CPValue.makeConstant(Integer.MIN_VALUE);

        // equals test
        Assert.assertTrue(CPValue.makeConstant(0).equals(CPValue.makeConstant(0)));
        Assert.assertFalse(CPValue.makeConstant(0).equals(CPValue.makeConstant(1)));

        Assert.assertTrue(max.equals(CPValue.makeConstant(Integer.MAX_VALUE)));
        Assert.assertFalse(max.equals(CPValue.getUndef()));
        Assert.assertTrue(min.equals(CPValue.makeConstant(Integer.MIN_VALUE)));
        Assert.assertFalse(min.equals(CPValue.getNAC()));

        // hash test
        Set<CPValue> cpValueSet = new HashSet<>();

        cpValueSet.add(min);
        Assert.assertFalse(cpValueSet.contains(CPValue.getUndef()));
        cpValueSet.add(CPValue.getUndef());

        cpValueSet.add(max);
        Assert.assertFalse(cpValueSet.contains(CPValue.getNAC()));
        cpValueSet.add(CPValue.getNAC());

        Assert.assertEquals(4, cpValueSet.size());
    }
}
