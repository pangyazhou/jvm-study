package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassReader;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/20 上午9:58
 **/
@Data
public class ConstantPackageInfo extends ConstantInfo {
    private int tag = ConstantPoolConstants.PACKAGE;
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构, 表示包名称
     */
    private int nameIndex;

    private ConstantPoolInfo constantPool;

    public ConstantPackageInfo(ConstantPoolInfo constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.nameIndex = reader.readUShort();
    }
}
