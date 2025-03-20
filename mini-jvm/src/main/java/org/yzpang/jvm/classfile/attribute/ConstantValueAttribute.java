package org.yzpang.jvm.classfile.attribute;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc: 定长属性
 *       field_info结构的属性表中
 *       静态字段: 字段值=ConstantValue值
 *       非静态字段: 忽略属性
 * field_info属性表中最多一个ConstantValue属性
 * Date: 2025/3/18 下午1:41
 **/
@Data
public class ConstantValueAttribute extends AttributeInfo {
    /**
     * 常量池有效索引, 索引处成员为该属性的常量值.
     * long: Constant_Long
     * float: Constant_Float
     * double: Constant_Double
     * int, short, char, byte, boolean: Constant_Integer
     * String: Constant_String
     */
    private int ConstantValueIndex;
}
