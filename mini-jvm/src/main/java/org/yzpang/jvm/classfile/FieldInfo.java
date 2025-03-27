package org.yzpang.jvm.classfile;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 字段表
 * Date: 2025/3/18 上午11:28
 **/
@Data
public class FieldInfo {
    /**
     * u2 修饰符
     */
    private int accessFlags;
    /**
     * u2 常量池的有效索引, 字段的简单名称
     */
    private int nameIndex;
    /**
     * u2 常量池的有效索引, 字段的描述符
     */
    private int descriptorIndex;
    /**
     * u2 属性表数量
     */
    private int attributeCount;
    private AttributeInfo[] attributes;

    private ClassFile classFile;
}
