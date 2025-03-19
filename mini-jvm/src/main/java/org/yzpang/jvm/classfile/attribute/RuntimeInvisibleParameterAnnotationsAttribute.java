package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          method_info结构的属性表中
 *          用于保存标注在对应方法的形式参数声明上面的运行时不可见注解
 * Date: 2025/3/19 下午2:51
 **/
public class RuntimeInvisibleParameterAnnotationsAttribute extends AttributeInfo {
    private short numParameters;
    private ParameterAnnotation[] parameterAnnotations;
}
