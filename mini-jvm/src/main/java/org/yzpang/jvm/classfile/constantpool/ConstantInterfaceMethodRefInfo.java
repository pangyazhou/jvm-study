package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
public class ConstantInterfaceMethodRefInfo extends ConstantMemberRefInfo {
    private int tag = ConstantPoolConstants.INTERFACEMETHODREF;

    public ConstantInterfaceMethodRefInfo(ConstantPoolInfo constantPool) {
        super(constantPool);
    }
}
