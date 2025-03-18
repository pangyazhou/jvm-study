package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:   变长属性
 *          classFile结构中
 * Date: 2025/3/18 下午4:40
 **/
public class InnerClassesAttribute extends AttributeInfo {
    /**
     * classes数组数量
     */
    private int numberOfClasses;

}
