package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc:    参数注解属性
 * Date: 2025/3/19 下午2:47
 **/
public class ParameterAnnotation {
    /**
     * 注解数量
     */
    private short numAnnotations;
    private Annotations[] annotations;
}
