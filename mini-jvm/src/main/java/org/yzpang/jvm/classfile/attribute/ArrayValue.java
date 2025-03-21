package org.yzpang.jvm.classfile.attribute;

import lombok.Data;

/**
 * Author: yzpang
 * Desc:   数组结构
 * Date: 2025/3/19 下午2:27
 **/
@Data
public class ArrayValue {
    /**
     * u2 数组的成员个数
     */
    private short numValues;
    private ElementValue[] values;
}
