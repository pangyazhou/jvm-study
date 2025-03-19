package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          ClassFile/field_info/method_info结构的属性表中
 *          该属性记录了添加在类声明/字段声明/方法声明上面,且于运行时可见的注解
 *          属性表中最多有一个该属性
 * Date: 2025/3/19 下午2:11
 **/
public class RuntimeVisibleAnnotationsAttribute extends AttributeInfo {
    /**
     * 运行时可见注解的数量
     */
    private short numAnnotations;
    private Annotations[] annotations;
}
