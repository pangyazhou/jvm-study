package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantMemberRefInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMemberRef;

public class FieldRefConstant<T> extends CustomMemberRef {


    public FieldRefConstant(CustomConstantPool constantPool, ConstantMemberRefInfo memberRefInfo) {
        this.constantPool = constantPool;
    }
}
