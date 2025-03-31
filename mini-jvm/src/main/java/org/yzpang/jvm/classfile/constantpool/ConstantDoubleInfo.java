package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassReader;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:14
 **/
@Data
public class ConstantDoubleInfo extends ConstantInfo {
    private int tag = ConstantPoolConstants.DOUBLE;

    /**
     * double数据 u8
     */
    private double bytes;

    @Override
    public void readInfo(ClassReader reader) {
        this.bytes = Double.longBitsToDouble(reader.readLong());
    }
}
