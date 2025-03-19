package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc:   枚举常量
 * Date: 2025/3/19 下午2:27
 **/
public class EnumConstValue {
    /**
     * u2  常量池的有效索引, 指向Constant_Utf8_info结构, 表示一个有效的字段描述符
     */
    private short typeNameIndex;

    /**
     * u2  常量池的有效索引, 指向Constant_Utf8_info结构, 给出了当前element_value结构所表示的枚举常量的简单名称
     */
    private short constNameIndex;
}
