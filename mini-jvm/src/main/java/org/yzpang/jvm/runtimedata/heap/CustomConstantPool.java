package org.yzpang.jvm.runtimedata.heap;

import lombok.Getter;
import org.yzpang.jvm.classfile.ConstantInfo;
import org.yzpang.jvm.classfile.ConstantPoolInfo;
import org.yzpang.jvm.classfile.constantpool.*;
import org.yzpang.jvm.classfile.util.ConstantPoolUtil;
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
    public CustomConstantPool(CustomClass clazz, ConstantPoolInfo constantPoolInfo) {
        this.clazz = clazz;

        ConstantInfo[] constantInfos = constantPoolInfo.getConstantInfos();
        this.constants = new CustomConstant[constantInfos.length];
        for (int i = 0; i < constantInfos.length; i++) {
            if (constantInfos[i] instanceof ConstantIntegerInfo) {
                // int
                int value = ConstantPoolUtil.getInteger(constantPoolInfo, i);
                this.constants[i] = new IntegerConstant(value);
            } else if (constantInfos[i] instanceof ConstantLongInfo) {
                // long
                long value = ConstantPoolUtil.getLong(constantPoolInfo, i);
                this.constants[i] = new LongConstant(value);
                i++;
            } else if (constantInfos[i] instanceof ConstantFloatInfo) {
                // float
                float value = ConstantPoolUtil.getFloat(constantPoolInfo, i);
                this.constants[i] = new FloatConstant(value);
            } else if (constantInfos[i] instanceof ConstantDoubleInfo) {
                // double
                double value = ConstantPoolUtil.getDouble(constantPoolInfo, i);
                this.constants[i] = new DoubleConstant(value);
                i++;
            } else if (constantInfos[i] instanceof ConstantUtf8Info) {
                // utf8
                String str = ((ConstantUtf8Info) constantInfos[i]).getStr();
                this.constants[i] = new StringConstant(str);
            } else if (constantInfos[i] instanceof ConstantStringInfo) {
                // string
                String str = ((ConstantStringInfo) constantInfos[i]).getString();
                this.constants[i] = new StringConstant(str);
            } else if (constantInfos[i] instanceof ConstantClassInfo) {
                // class
                ClassRef classRef = new ClassRef(this, (ConstantClassInfo) constantInfos[i]);
                this.constants[i] = classRef;
            } else if (constantInfos[i] instanceof ConstantFieldRefInfo) {
                // fields
                FieldRef fieldRef = new FieldRef(this, (ConstantFieldRefInfo) constantInfos[i]);
                this.constants[i] = fieldRef;
            } else if (constantInfos[i] instanceof ConstantMethodRefInfo) {
                MethodRef methodRef = new MethodRef(this, (ConstantMethodRefInfo) constantInfos[i]);
                this.constants[i] = methodRef;
            } else if (constantInfos[i] instanceof ConstantInterfaceMethodRefInfo) {
                InterfaceMethodRef interfaceMethodRef = new InterfaceMethodRef(this, (ConstantInterfaceMethodRefInfo) constantInfos[i]);
                this.constants[i] = interfaceMethodRef;
            }
        }
    }


    public CustomConstant getConstant(int index) {
        if (index < 0 || index >= constants.length) {
            throw new IndexOutOfBoundsException("No constants at index: " + index);
        }
        return constants[index];
    }

}
