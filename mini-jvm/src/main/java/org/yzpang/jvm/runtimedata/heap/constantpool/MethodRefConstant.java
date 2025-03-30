package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantMemberRefInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMemberRef;

public class MethodRefConstant extends CustomMemberRef {
    public MethodRefConstant(CustomConstantPool constantPool, ConstantMemberRefInfo memberRefInfo) {
        this.constantPool = constantPool;
    }
}
