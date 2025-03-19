package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          类文件结构中
 *          用于保存invokedynamic指令引用的引导方法限定符
 * Date: 2025/3/19 下午1:49
 **/
public class BootstrapMethodsAttribute extends AttributeInfo {
    private short numBootstrapMethods;
    private BootstrapMethod[] bootstrapMethods;
}
