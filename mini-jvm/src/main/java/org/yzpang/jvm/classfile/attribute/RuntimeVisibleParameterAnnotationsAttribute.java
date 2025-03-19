package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          method_info结构的属性表中
 *          该属性记录了标注在对应方法的形式参数声明上面的运行时可见注解.
 *          每个method_info结构的属性表最多一个该属性
 * Date: 2025/3/19 下午2:44
 **/
public class RuntimeVisibleParameterAnnotationsAttribute extends AttributeInfo {
    /**
     * 方法中形式参数的个数
     */
    private short numParameters;
    /**
     * 每个成员表示一个形式参数的所有运行时可见注解
     */
    private ParameterAnnotation[] parameterAnnotations;
}
