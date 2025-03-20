package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/20 上午9:52
 **/
@Data
public class ConstantDynamicInfo extends ConstantPoolInfo{
    private int tag = ConstantPoolConstants.DYNAMIC;
    /**
     * u2 当前Class文件中引导方法表的bootstrap_methods[]数组中的有效索引
     */
    private int bootstrapMethodAttrIndex;
    /**
     * u2 常量池的有效索引, 指向Constant_NameAndType_info结构, 表示方法名和方法描述符
     */
    private int nameAndTypeIndex;
}
