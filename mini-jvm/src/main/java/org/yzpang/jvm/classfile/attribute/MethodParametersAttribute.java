package org.yzpang.jvm.classfile.attribute;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          method_info结构
 *          记录了与形式参数有关的信息,如参数名称等.
 *          最多只有一个该属性
 * Date: 2025/3/19 下午1:58
 **/
@Data
public class MethodParametersAttribute extends AttributeInfo {
    /**
     * u1 参数个数
     */
    private int parameterCount;

    private Parameters[] parameters;
}
