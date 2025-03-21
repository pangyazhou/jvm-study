package org.yzpang.jvm.classfile.attribute;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          类文件结构中
 *          用于保存invokedynamic指令引用的引导方法限定符
 * Date: 2025/3/19 下午1:49
 **/
@Data
public class BootstrapMethodsAttribute extends AttributeInfo {
    /**
     * u2
     */
    private int numBootstrapMethods;
    private BootstrapMethod[] bootstrapMethods;
}
