package org.yzpang.jvm.runtimedata.heap;

import lombok.Getter;
import org.yzpang.jvm.classfile.ConstantInfo;
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
    public static CustomConstantPool newConstantPool(CustomClass clazz, ConstantInfo[] constantInfos) {
        CustomConstantPool customConstantPool = new CustomConstantPool();
        customConstantPool.clazz = clazz;
        customConstantPool.constants = new CustomConstant[constantInfos.length];
        for (int i = 0; i < constantInfos.length; i++) {
            if (constantInfos[i] instanceof ConstantIntegerInfo) {
                // int
                int value = ClassFileUtil.getIntegerInfo(constantInfos, i);
                customConstantPool.constants[i] = new IntegerConstant(value);
            } else if (constantInfos[i] instanceof ConstantLongInfo) {
                // long
                long value = ClassFileUtil.getLongInfo(constantInfos, i);
                customConstantPool.constants[i] = new LongConstant(value);
                i++;
            } else if (constantInfos[i] instanceof ConstantFloatInfo) {
                // float
                float value = ClassFileUtil.getFloatInfo(constantInfos, i);
                customConstantPool.constants[i] = new FloatConstant(value);
            } else if (constantInfos[i] instanceof ConstantDoubleInfo) {
                // double
                double value = ClassFileUtil.getDoubleInfo(constantInfos, i);
                customConstantPool.constants[i] = new DoubleConstant(value);
                i++;
            } else if (constantInfos[i] instanceof ConstantUtf8Info) {
                // string
                String str = ClassFileUtil.getUtf8Info(constantInfos, i);
                customConstantPool.constants[i] = new StringConstant(str);
            } else if (constantInfos[i] instanceof ConstantStringInfo) {
                // string
                String str = ClassFileUtil.getStringInfo(constantInfos, i);
                customConstantPool.constants[i] = new StringConstant(str);
            } else if (constantInfos[i] instanceof ConstantClassInfo) {
//                customConstantPool.constants[i] = new ClassRefConstant(new Object());
            } else if (constantInfos[i] instanceof ConstantFieldRefInfo) {
//                customConstantPool.constants[i] = new FieldRefConstant(new Object());
            } else if (constantInfos[i] instanceof ConstantMethodRefInfo) {
//                customConstantPool.constants[i] = new MethodRefConstant(new Object());
            } else if (constantInfos[i] instanceof ConstantInterfaceMethodRefInfo) {
//                customConstantPool.constants[i] = new InterfaceMethodRefConstant(new Object());
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
