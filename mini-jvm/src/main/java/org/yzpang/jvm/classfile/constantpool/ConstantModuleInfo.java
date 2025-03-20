package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/20 上午9:57
 **/
@Data
public class ConstantModuleInfo extends ConstantPoolInfo {
    private int tag = ConstantPoolConstants.MODULE;
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构, 表示模块名称
     */
    private int nameIndex;
}
