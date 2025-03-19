package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *      method_info结构中
 *      该属性记录了由method_info结构表示的元素的默认值
 * Date: 2025/3/19 下午2:07
 **/
public class AnnotationDefaultAttribute extends AttributeInfo {
    private short numAnnotations;
    private Annotations[] annotations;
}
