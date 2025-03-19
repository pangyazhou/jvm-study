package org.yzpang.jvm.classfile.attribute;

import org.yzpang.jvm.classfile.AttributeInfo;

/**
 * Author: yzpang
 * Desc:    定长属性
 *          类/字段/方法结构
 *          Java反射API获取的的泛型类型数据来源
 * Date: 2025/3/19 下午1:46
 **/
public class SignatureAttribute extends AttributeInfo {
    /**
     * 常量池的有效索引, 指向Constant_Utf8_info结构, 表示类签名或方法类型签名或字段类型签名
     */
    private int signatureIndex;
}
