package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
public class ConstantMethodTypeInfo extends ConstantPoolInfo {
    private int tag = ConstantInfoEnum.METHOD_TYPE.ordinal();
    /**
     * 常量池的有效索引, 指向Constant_Utf8_info结构, 此结构表示方法的描述符
     */
    private int descriptorIndex;
}
