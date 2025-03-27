package org.yzpang.jvm.classfile;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 属性表
 * Date: 2025/3/18 上午11:30
 **/
@Data
public class AttributeInfo {
    /**
     * 属性名 u2 常量池有效索引 指向Constant_Utf8_info结构
     */
    private int attributeNameIndex;
    /**
     * 属性值长度 u4
     */
    private int attributeLength;

    private byte[] info;

    private ClassFile classFile;

}
