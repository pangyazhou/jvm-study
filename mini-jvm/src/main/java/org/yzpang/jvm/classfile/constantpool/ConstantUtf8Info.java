package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
@Data
public class ConstantUtf8Info extends ConstantPoolInfo{
    private int tag = ConstantPoolConstants.UTF8;
    /**
     * u2 字符串长度
     */
    private int length;
    /**
     * u1 字符数组
     */
    private byte[] bytes;
}
