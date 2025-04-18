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
public class ConstantIntegerInfo extends ConstantInfo {
    private int tag = ConstantPoolConstants.INTEGER;

    /**
     * int数据 u4
     */
    private int bytes;

    @Override
    public void readInfo(ClassReader reader) {
        this.bytes = reader.readInt();
    }
}
