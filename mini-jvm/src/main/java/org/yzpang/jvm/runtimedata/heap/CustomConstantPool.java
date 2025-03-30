package org.yzpang.jvm.runtimedata.heap;

import lombok.Getter;
import org.yzpang.jvm.classfile.constantpool.*;
import org.yzpang.jvm.classfile.util.ClassFileUtil;
import org.yzpang.jvm.runtimedata.heap.constantpool.*;

/**
 * 运行时常量池
 */
public class CustomConstantPool {
    @Getter
    private CustomClass clazz;

    private CustomConstant[] constants;

    /**
     * 将class文件的常量池转化为运行时常量池
     */
    public static CustomConstantPool newConstantPool(CustomClass clazz, ConstantPoolInfo[] constantPoolInfos) {
        CustomConstantPool customConstantPool = new CustomConstantPool();
        customConstantPool.clazz = clazz;
        customConstantPool.constants = new CustomConstant[constantPoolInfos.length];
        for (int i = 0; i < constantPoolInfos.length; i++) {
            if (constantPoolInfos[i] instanceof ConstantIntegerInfo) {
                // int
                int value = ClassFileUtil.getIntegerInfo(constantPoolInfos, i);
                customConstantPool.constants[i] = new IntegerConstant(value);
            } else if (constantPoolInfos[i] instanceof ConstantLongInfo) {
                // long
                long value = ClassFileUtil.getLongInfo(constantPoolInfos, i);
                customConstantPool.constants[i] = new LongConstant(value);
                i++;
            } else if (constantPoolInfos[i] instanceof ConstantFloatInfo) {
                // float
                float value = ClassFileUtil.getFloatInfo(constantPoolInfos, i);
                customConstantPool.constants[i] = new FloatConstant(value);
            } else if (constantPoolInfos[i] instanceof ConstantDoubleInfo) {
                // double
                double value = ClassFileUtil.getDoubleInfo(constantPoolInfos, i);
                customConstantPool.constants[i] = new DoubleConstant(value);
                i++;
            } else if (constantPoolInfos[i] instanceof ConstantUtf8Info) {
                // string
                String str = ClassFileUtil.getUtf8Info(constantPoolInfos, i);
                customConstantPool.constants[i] = new StringConstant(str);
            } else if (constantPoolInfos[i] instanceof ConstantStringInfo) {
                // string
                String str = ClassFileUtil.getStringInfo(constantPoolInfos, i);
                customConstantPool.constants[i] = new StringConstant(str);
            } else if (constantPoolInfos[i] instanceof ConstantClassInfo) {
//                customConstantPool.constants[i] = new ClassRefConstant(new Object());
            } else if (constantPoolInfos[i] instanceof ConstantFieldRefInfo) {
//                customConstantPool.constants[i] = new FieldRefConstant(new Object());
            } else if (constantPoolInfos[i] instanceof ConstantMethodRefInfo) {
//                customConstantPool.constants[i] = new MethodRefConstant(new Object());
            } else if (constantPoolInfos[i] instanceof ConstantInterfaceMethodRefInfo) {
                customConstantPool.constants[i] = new InterfaceMethodRefConstant(new Object());
            }
        }
        return customConstantPool;
    }


    public CustomConstant getConstant(int index) {
        if (index < 0 || index >= constants.length) {
            throw new IndexOutOfBoundsException("No constants at index: " + index);
        }
        return constants[index];
    }

}
