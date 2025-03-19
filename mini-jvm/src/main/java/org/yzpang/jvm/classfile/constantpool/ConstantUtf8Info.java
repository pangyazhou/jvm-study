package org.yzpang.jvm.classfile.constantpool;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
public class ConstantUtf8Info {
    private int tag = ConstantInfoEnum.UTF8.ordinal();
    /**
     * 字符串长度
     */
    private int length;
    /**
     * 字符数组
     */
    private byte[] bytes;
}
