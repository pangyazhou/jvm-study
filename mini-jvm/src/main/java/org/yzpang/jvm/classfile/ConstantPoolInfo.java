package org.yzpang.jvm.classfile;

import lombok.Getter;
import org.yzpang.jvm.classfile.constantpool.*;
import org.yzpang.jvm.classfile.util.ConstantPoolUtil;
import org.yzpang.jvm.constant.ConstantPoolConstants;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/31 下午4:01
 **/
@Getter
public class ConstantPoolInfo {
    private ConstantInfo[] constantInfos;

    public ConstantPoolInfo readConstantPoolInfo(ClassReader reader) throws Exception {
        // 常量池数量
        int cpCount= reader.readUShort();
        constantInfos = new ConstantInfo[cpCount];
        // 从1开始 cpCount - 1 个
        for (int i = 1; i < cpCount; i++) {
            constantInfos[i] = readConstantInfo(reader, this);
            if (constantInfos[i] instanceof ConstantLongInfo
                    || constantInfos[i] instanceof ConstantDoubleInfo) {
                i++;    // long与double占两个位置
            }
        }
        return this;
    }

    public ConstantInfo readConstantInfo(ClassReader reader, ConstantPoolInfo constantPoolInfo) throws Exception {
        int tag = reader.readUByte();
        ConstantInfo constantInfo = newConstantInfo(tag, constantPoolInfo);
        constantInfo.readInfo(reader);
        return constantInfo;
    }

    public ConstantInfo newConstantInfo(int tag, ConstantPoolInfo constantPoolInfo) throws Exception {
        switch (tag) {
            case ConstantPoolConstants.UTF8:
                ConstantUtf8Info constantUtf8Info = new ConstantUtf8Info();
                return constantUtf8Info;
            case ConstantPoolConstants.INTEGER:
                ConstantIntegerInfo constantIntegerInfo = new ConstantIntegerInfo();
                return constantIntegerInfo;
            case ConstantPoolConstants.FLOAT:
                ConstantFloatInfo constantFloatInfo = new ConstantFloatInfo();
                return constantFloatInfo;
            case ConstantPoolConstants.LONG:
                ConstantLongInfo constantLongInfo = new ConstantLongInfo();
                return constantLongInfo;
            case ConstantPoolConstants.DOUBLE:
                ConstantDoubleInfo constantDoubleInfo = new ConstantDoubleInfo();
                return constantDoubleInfo;
            case ConstantPoolConstants.CLASS:
                ConstantClassInfo constantClassInfo = new ConstantClassInfo(constantPoolInfo);
                return constantClassInfo;
            case ConstantPoolConstants.STRING:
                ConstantStringInfo constantStringInfo = new ConstantStringInfo(constantPoolInfo);
                return constantStringInfo;
            case ConstantPoolConstants.FIELDREF:
                ConstantFieldRefInfo constantFieldRefInfo = new ConstantFieldRefInfo(constantPoolInfo);
                return constantFieldRefInfo;
            case ConstantPoolConstants.METHODREF:
                ConstantMethodRefInfo constantMethodRefInfo = new ConstantMethodRefInfo(constantPoolInfo);
                return constantMethodRefInfo;
            case ConstantPoolConstants.INTERFACEMETHODREF:
                ConstantInterfaceMethodRefInfo constantInterfaceMethodRefInfo = new ConstantInterfaceMethodRefInfo(constantPoolInfo);
                return constantInterfaceMethodRefInfo;
            case ConstantPoolConstants.NAMEANDTYPE:
                ConstantNameAndTypeInfo constantNameAndTypeInfo = new ConstantNameAndTypeInfo(constantPoolInfo);
                return constantNameAndTypeInfo;
            case ConstantPoolConstants.METHOD_HANDLE:
                ConstantMethodHandleInfo constantMethodHandleInfo = new ConstantMethodHandleInfo(constantPoolInfo);
                return constantMethodHandleInfo;
            case ConstantPoolConstants.METHOD_TYPE:
                ConstantMethodTypeInfo constantMethodTypeInfo = new ConstantMethodTypeInfo(constantPoolInfo);
                return constantMethodTypeInfo;
            case ConstantPoolConstants.DYNAMIC:
                ConstantDynamicInfo constantDynamicInfo = new ConstantDynamicInfo(constantPoolInfo);
                return constantDynamicInfo;
            case ConstantPoolConstants.INVOKEDYNAMIC:
                ConstantInvokeDynamicInfo constantInvokeDynamicInfo = new ConstantInvokeDynamicInfo(constantPoolInfo);
                return constantInvokeDynamicInfo;
            case ConstantPoolConstants.MODULE:
                ConstantModuleInfo constantModuleInfo = new ConstantModuleInfo(constantPoolInfo);
                return constantModuleInfo;
            case ConstantPoolConstants.PACKAGE:
                ConstantPackageInfo constantPackageInfo = new ConstantPackageInfo(constantPoolInfo);
                return constantPackageInfo;
            default:
                throw new Exception("标志错误: " + tag);
        }
    }

    public ConstantInfo getConstantPoolInfo(int index) {
        if (index < 0 || index >= constantInfos.length){
            throw new IndexOutOfBoundsException("No constants at index: " + index);
        }
        return constantInfos[index];
    }

    public String getUtf8(int index) {
        return ConstantPoolUtil.getUtf8(this, index);
    }

    public String getClassName(int index) {
        if (index > 0 && index < constantInfos.length) {
            ConstantClassInfo constantClassInfo = (ConstantClassInfo) constantInfos[index];
            return constantClassInfo.getName();
        }
        return "";
    }

    public String[] getNameAndType(int index) {
        String[] nameAndType = new String[2];
        ConstantNameAndTypeInfo constantNameAndTypeInfo =  (ConstantNameAndTypeInfo) constantInfos[index];
        nameAndType[0] = getUtf8(constantNameAndTypeInfo.getNameIndex());
        nameAndType[1] = getUtf8(constantNameAndTypeInfo.getDescriptorIndex());
        return nameAndType;
    }
}
