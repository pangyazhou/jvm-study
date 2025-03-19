package org.yzpang.jvm.classfile.attribute;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/19 下午1:52
 **/
public class BootstrapMethod {
    /**
     * 常量池的有效索引, 指向Constant_MethodHandle_info结构,
     */
    private int bootstrap_method_ref;
    private int numBootstrapArguments;
    /**
     * 常量池的有效索引,指向以下结构:
     * Constant_String_info
     * Constant_Class_info
     * Constant_Integer_info
     * Constant_Long_info
     * Constant_Float_info
     * Constant_Double_info
     * Constant_MethodHandle_info
     * Constant_MethodType_info
     */
    private int[] bootstrapArguments;
}
