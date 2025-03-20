package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
@Data
public class ConstantDoubleInfo extends ConstantPoolInfo{
    private int tag = ConstantPoolConstants.DOUBLE;

    /**
     * double数据 u8
     */
    private double bytes;
}
