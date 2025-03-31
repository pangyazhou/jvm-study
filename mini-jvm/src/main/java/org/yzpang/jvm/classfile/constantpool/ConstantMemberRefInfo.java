package org.yzpang.jvm.classfile.constantpool;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassReader;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;

@Data
public class ConstantMemberRefInfo extends ConstantInfo {
    /**
     * u2 常量池的有效索引, 指向Constant_Class_Info结构, 可以是类或接口
     */
    protected int classIndex;
    /**
     * u2 常量池的有效索引, 指向Constant_NameAndType_info结构, 必须是类描述符
     */
    protected int nameAndTypeIndex;

    protected ConstantPoolInfo constantPool;

    public ConstantMemberRefInfo(ConstantPoolInfo constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.classIndex = reader.readUByte();
        this.nameAndTypeIndex = reader.readUByte();
    }

    public String getClassName(){
        return this.constantPool.getClassName(classIndex);
    }

    public String[] getNameAndType(){
        return this.constantPool.getNameAndType(nameAndTypeIndex);
    }
}
