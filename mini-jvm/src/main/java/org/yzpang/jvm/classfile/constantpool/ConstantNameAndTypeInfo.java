package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
@Data
public class ConstantNameAndTypeInfo extends ConstantPoolInfo {
    private int tag = ConstantPoolConstants.NAMEANDTYPE;
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_Info结构, 表示特殊的方法名<init>, 或者有效的字段或方法的非限定名
     */
    private int nameIndex;
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构, 表示一个字段描述符或方法描述符
     */
    private int descriptorIndex;
}
