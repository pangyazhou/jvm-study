package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/20 上午9:52
 **/
public class ConstantDynamicInfo extends ConstantMemberDynamicInfo {
    protected int tag = ConstantPoolConstants.DYNAMIC;

    public ConstantDynamicInfo(ConstantPoolInfo constantPool) {
        super(constantPool);
    }
}
