package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantClassInfo;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomSymbolRef;

public class ClassRefConstant extends CustomSymbolRef implements CustomConstant {

    public ClassRefConstant(CustomConstantPool constantPool, ConstantClassInfo classInfo) {
        this.constantPool = constantPool;
        this.className = classInfo.getName();
    }
}
