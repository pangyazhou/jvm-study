package org.yzpang.jvm.classfile;

/**
 * Author: yzpang
 * Desc: 方法表
 * Date: 2025/3/18 上午11:28
 **/
public class MethodInfo {
    /**
     * 方法修饰符
     */
    public int accessFlags;
    /**
     * 常量池的有效索引, 方法的简单名称
     */
    private int nameIndex;
    /**
     * 常量池的有效索引, 方法的描述符
     */
    private int descriptorIndex;
    /**
     * 属性计数器
     */
    private int attributeCount;
    /**
     * 属性表
     */
    private AttributeInfo[] attributes;
}
