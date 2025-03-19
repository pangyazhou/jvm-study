package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          ClassFile/field_info/method_info结构的属性表中.
 *          用于保存标注在类/方法/字段声明上的运行时非可见注解
 *          最多只能有一个该属性
 * Date: 2025/3/19 下午2:41
 **/
public class RuntimeInvisibleAnnotationsAttribute extends AttributeInfo {
    /**
     * u2 注解的数量
     */
    private int numAnnotations;
    private Annotations[] annotations;
}
