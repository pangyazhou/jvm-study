package org.yzpang.jvm.classfile;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 方法表
 * Date: 2025/3/18 上午11:28
 **/
@Data
public class MethodInfo {
    /**
     * u2 方法修饰符
     */
    public int accessFlags;
    /**
     * u2 常量池的有效索引, 方法的简单名称
     */
    private int nameIndex;
    /**
     * u2 常量池的有效索引, 方法的描述符
     */
    private int descriptorIndex;
    /**
     * u2 属性计数器
     */
    private int attributeCount;
    /**
     * u2 属性表
     */
    private AttributeInfo[] attributes;
}
