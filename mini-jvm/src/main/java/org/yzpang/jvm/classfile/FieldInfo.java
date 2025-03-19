package org.yzpang.jvm.classfile;

/**
 * Author: yzpang
 * Desc: 字段表
 * Date: 2025/3/18 上午11:28
 **/
public class FieldInfo {
    /**
     * 修饰符
     */
    private int accessFlags;
    /**
     * 常量池的有效索引, 字段的简单名称
     */
    private int nameIndex;
    /**
     * 常量池的有效索引, 字段的描述符
     */
    private int descriptorIndex;
    private int attributeCount;
    private AttributeInfo[] attributes;
}
