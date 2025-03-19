package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc: Constant_class_info
 * Date: 2025/3/18 上午10:09
 **/
public class ConstantStringInfo extends ConstantPoolInfo{
    private int tag = ConstantInfoEnum.STRING.ordinal();
    /**
     * 常量池的索引值, 指向常量池中一个CONSTANT_Utf8_info类型常量
     */
    private int stringIndex;
}
