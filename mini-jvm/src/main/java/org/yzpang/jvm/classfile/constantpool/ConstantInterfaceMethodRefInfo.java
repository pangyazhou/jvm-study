package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
@Data
public class ConstantInterfaceMethodRefInfo extends ConstantPoolInfo {
    private int tag = ConstantPoolConstants.INTERFACEMETHODREF;
    /**
     * 常量池的有效索引, 指向Constant_Class_Info结构, 必须是接口
     */
    private int classIndex;
    /**
     * 常量池的有效索引, 指向Constant_NameAndType_info结构, 必须是方法描述符
     */
    private int nameAndTypeIndex;
}
