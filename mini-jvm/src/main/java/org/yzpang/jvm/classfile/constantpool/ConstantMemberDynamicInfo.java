package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassReader;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/20 上午9:52
 **/
@Data
public class ConstantMemberDynamicInfo extends ConstantInfo {
    /**
     * u2 当前Class文件中引导方法表的bootstrap_methods[]数组中的有效索引
     */
    protected int bootstrapMethodAttrIndex;
    /**
     * u2 常量池的有效索引, 指向Constant_NameAndType_info结构, 表示方法名和方法描述符
     */
    protected int nameAndTypeIndex;

    private ConstantPoolInfo constantPool;

    public ConstantMemberDynamicInfo(ConstantPoolInfo constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.bootstrapMethodAttrIndex = reader.readUShort();
        this.nameAndTypeIndex = reader.readUShort();
    }
}
