package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:   变长属性
 *          Code属性的属性表中
 *          用于虚拟机的类型检查验证阶段
 *          Code属性的属性表中最多有一个此属性
 * Date: 2025/3/18 下午3:20
 **/
public class StackMapTableAttribute extends AttributeInfo {
    /**
     * entries表中成员数量
     */
    private int numberOfEntries;

    /**
     * 本方法的栈映射帧
     */
    private StackMapFrame[] entries;
}
