package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
@Data
public class ConstantFieldRefInfo extends ConstantPoolInfo {
    private int tag = ConstantPoolConstants.FIELDREF;
    /**
     * u2 常量池的有效索引, 指向Constant_Class_Info结构, 可以是类或接口
     */
    private int classIndex;
    /**
     * u2 常量池的有效索引, 指向Constant_NameAndType_info结构, 必须是类描述符
     */
    private int nameAndTypeIndex;
}
