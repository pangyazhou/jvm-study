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
public class ConstantMethodHandleInfo extends ConstantInfo {
    protected int tag = ConstantPoolConstants.METHOD_HANDLE;
    /**
     * u1 1-9范围内,表示方法句柄的类型
     */
    private int referenceKind;
    /**
     * u2 常量池的有效索引
     *
     * referenceKind值             指向                        说明
     *    1,2,3,4        Constant_Fieldref_info          此结构表示某个字段
     *    5,8            Constant_Methodref_info         此结构表示类中的某个方法或构造器
     *    6,7            Constant_Methodref_info/Constant_InterfaceMethodref_info              接口或类中的方法
     *    9              Constant_InterfaceMethodref_info 此结构表示接口中的方法
     *    5,6,7,9        Constant_Methodref_info/Constant_InterfaceMethodref_info  此结构表示的方法名称不能为<init>或<cinit>
     *    8              Constant_Methodref_info          此结构表示的方法,名称必须为<init>
     */
    private int referenceIndex;

    private ConstantPoolInfo constantPool;

    public ConstantMethodHandleInfo(ConstantPoolInfo constantPool) {
        this.constantPool = constantPool;
    }

    @Override
    public void readInfo(ClassReader reader) {
        this.referenceKind = reader.readUByte();
        this.referenceIndex = reader.readUShort();
    }
}
