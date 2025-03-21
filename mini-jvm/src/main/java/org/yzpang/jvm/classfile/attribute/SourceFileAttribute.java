package org.yzpang.jvm.classfile.attribute;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    定长属性
 *          ClassFile结构
 * Date: 2025/3/19 上午11:19
 **/
@Data
public class SourceFileAttribute extends AttributeInfo {
    /**
     * 常量池有效索引, 指向Constant_Utf8_info结构, 常量值是源码文件的文件名
     */
    private int sourceFileIndex;
}
