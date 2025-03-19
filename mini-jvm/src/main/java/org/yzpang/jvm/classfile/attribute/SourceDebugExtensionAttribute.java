package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    变长属性
 *          ClassFile结构中
 * Date: 2025/3/19 上午11:26
 **/
public class SourceDebugExtensionAttribute extends AttributeInfo {
    /**
     * 额外的调试信息
     */
    private byte[] debugExtension;
}
