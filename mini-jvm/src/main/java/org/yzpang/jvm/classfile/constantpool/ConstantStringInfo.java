package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassReader;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.constant.ConstantPoolConstants;

/**
 * Author: yzpang
 * Desc: Constant_class_info
 * Date: 2025/3/18 上午10:09
 **/
@Data
public class ConstantStringInfo extends ConstantInfo {
    private int tag = ConstantPoolConstants.STRING;
    /**
     * u2 常量池的索引值, 指向常量池中一个CONSTANT_Utf8_info类型常量
     */
    private int stringIndex;

    private ConstantPoolInfo constantPool;

    public ConstantStringInfo(ConstantPoolInfo constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.stringIndex = reader.readUShort();
    }

    public String getString(){
        return this.constantPool.getUtf8(this.stringIndex);
    }
}
