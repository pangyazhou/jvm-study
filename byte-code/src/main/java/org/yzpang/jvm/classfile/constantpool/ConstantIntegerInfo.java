package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
public class ConstantIntegerInfo {
    private int tag = ConstantInfoEnum.INTEGER.ordinal();

    /**
     * int数据 u4
     */
    private int bytes;
}
