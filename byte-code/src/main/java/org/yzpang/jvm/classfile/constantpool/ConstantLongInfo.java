package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
public class ConstantLongInfo {
    private int tag = ConstantInfoEnum.LONG.ordinal();

    /**
     * long数据 u8
     */
    private long bytes;
}
