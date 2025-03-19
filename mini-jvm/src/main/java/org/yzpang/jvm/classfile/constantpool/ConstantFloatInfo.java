package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
public class ConstantFloatInfo {
    private int tag = ConstantInfoEnum.FLOAT.ordinal();

    /**
     * float数据 u4
     */
    private float bytes;
}
