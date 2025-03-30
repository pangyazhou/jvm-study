package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantClassInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomSymbolRef;

public class ClassRefConstant<T> extends CustomSymbolRef implements CustomConstant<T> {

    public ClassRefConstant(CustomConstantPool constantPool, ConstantClassInfo classInfo) {
        this.constantPool = constantPool;
        StringConstant constant = (StringConstant)constantPool.getConstant(classInfo.getNameIndex());
        this.className = constant.get();
        this.clazz = constantPool.getClazz();
    }

    @Override
    public T get() {
        return null;
    }
}
