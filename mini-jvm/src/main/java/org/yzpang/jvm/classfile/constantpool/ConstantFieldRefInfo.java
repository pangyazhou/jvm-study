package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
public class ConstantFieldRefInfo extends ConstantMemberRefInfo {
    private int tag = ConstantPoolConstants.FIELDREF;

}
