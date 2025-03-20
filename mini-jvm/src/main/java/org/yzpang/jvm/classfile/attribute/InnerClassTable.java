package org.yzpang.jvm.classfile.attribute;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 内部类结构体
 * Date: 2025/3/18 下午5:10
 **/
@Data
public class InnerClassTable {
    /**
     * u2 常量池的有效索引, 指向Constant_Class_info结构, 表示类或者接口C
     */
    private int innerClassInfoIndex;
    /**
     * u2 若C不是类或者接口的成员/局部类/匿名类, 值为0
     * 常量池的有效索引, 指向Constant_Class_info结构, 表示类或者接口, C为这个类或者接口的成员
     */
    private int outerClassInfoIndex;
    /**
     * u2 匿名类, 值为0
     * 常量池的有效索引, 指向Constant_Utf8_info结构, 表示C的原始简单名称
     */
    private int innerNameIndex;
    /**
     * u2 访问标志
     */
    private int innerClassAccessFlags;
}
