package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/19 下午2:23
 **/
public class ElementValue {
    /**
     * u1 使用ASCII字符表示键值对中的值是什么类型
     */
    private char tag;
    /**
     * u2 B/C/D/F/I/J/S/Z/s  表示键值对中的值是个原始类型的常量值或者String类型的字面量
     */
    private short constValueIndex;

    /**
     * e 键值对中的值是个枚举常量
     */
    private EnumConstValue enumConstValue;

    /**
     * u2 c 常量池的有效索引, 指向Constant_Utf8_info结构, 表示返回描述符.
     */
    private short classInfoIndex;

    /**
     * @ 键值对中的值又是个注解
     */
    private Annotations annotationsValue;
    /**
     * [ 键值对中的值是个数组
     */
    private ArrayValue arrayValue;
}
