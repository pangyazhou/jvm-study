package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          ClassFile/field_info/method_info/Code结构中的属性表
 *          该属性记录了标注在对应类声明/字段声明/方法声明所使用的类型上面的运行时可见注解
 *          泛型类/接口/方法/构造器的类型参数声明上面的运行时可见注解
 * Date: 2025/3/19 下午3:16
 **/
public class RuntimeVisibleTypeAnnotationsAttribute extends AttributeInfo {
    /**
     * 运行时可见的类型注解数量
     */
    private short numAnnotations;

    private TypeAnnotation[] typeAnnotations;
}
