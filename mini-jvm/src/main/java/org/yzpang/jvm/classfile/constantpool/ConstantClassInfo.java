package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc: Constant_class_info
 * Date: 2025/3/18 上午10:09
 **/
@Data
public class ConstantClassInfo extends ConstantPoolInfo{
    private int tag = ConstantPoolConstants.CLASS;
    /**
     * 常量池的索引值, 指向常量池中一个CONSTANT_Utf8_info类型常量.此常量代表了这个类(或接口)的全限定名.
     */
    private int nameIndex;
}
