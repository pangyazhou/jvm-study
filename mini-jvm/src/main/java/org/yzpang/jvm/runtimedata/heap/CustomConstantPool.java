package org.yzpang.jvm.runtimedata.heap;

import org.yzpang.jvm.classfile.constantpool.ConstantPoolInfo;

/**
 * 运行时常量池
 */
public class CustomConstantPool {
    private CustomClass clazz;

    private CustomConstant[] constants;

    public static CustomConstantPool newConstantPool(CustomClass clazz, ConstantPoolInfo[] constantPoolInfos) {
        CustomConstantPool customConstantPool = new CustomConstantPool();
        customConstantPool.clazz = clazz;
        customConstantPool.constants = new CustomConstant[constantPoolInfos.length];
        for (int i = 0; i < constantPoolInfos.length; i++) {
            
        }
        return new CustomConstantPool();
    }


    public CustomConstant getConstant(int index) {
        return constants[index];
    }
}
