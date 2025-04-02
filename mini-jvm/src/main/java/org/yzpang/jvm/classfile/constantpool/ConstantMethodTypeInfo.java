package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassReader;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/18 上午10:26
 **/
@Data
public class ConstantMethodTypeInfo extends ConstantInfo {
    protected int tag = ConstantPoolConstants.METHOD_TYPE;
    /**
     * u2 常量池的有效索引, 指向Constant_Utf8_info结构, 此结构表示方法的描述符
     */
    private int descriptorIndex;

    private ConstantPoolInfo constantPool;

    public ConstantMethodTypeInfo(ConstantPoolInfo constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.descriptorIndex = reader.readUShort();
    }
}
