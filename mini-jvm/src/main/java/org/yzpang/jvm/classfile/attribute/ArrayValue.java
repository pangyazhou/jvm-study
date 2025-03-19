package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc:   数组结构
 * Date: 2025/3/19 下午2:27
 **/
public class ArrayValue {
    /**
     * u2 数组的成员个数
     */
    private short numValues;
    private ElementValue[] values;
}
