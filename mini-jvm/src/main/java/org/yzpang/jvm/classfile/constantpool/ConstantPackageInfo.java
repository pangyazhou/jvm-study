package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/20 上午9:58
 **/
@Data
public class ConstantPackageInfo extends ConstantPoolInfo {
    private int tag = ConstantPoolConstants.PACKAGE;
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构, 表示包名称
     */
    private int nameIndex;
}
