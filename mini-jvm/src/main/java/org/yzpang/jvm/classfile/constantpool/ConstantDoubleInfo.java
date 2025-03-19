package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
public class ConstantDoubleInfo {
    private int tag = ConstantInfoEnum.DOUBLE.ordinal();

    /**
     * double数据 u8
     */
    private double bytes;
}
