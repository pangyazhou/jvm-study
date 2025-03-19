package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          ClassFile/field_info/method_info/Code结构的属性表中,
 *          记录了标注在对应类/字段/方法声明所使用的类型上面的运行时不可见注解
 * Date: 2025/3/19 下午3:47
 **/
public class RuntimeInvisibleTypeAnnotationsAttribute extends AttributeInfo {
    /**
     * u2
     */
    private short numAnnotations;
    private TypeAnnotation[] typeAnnotations;
}
