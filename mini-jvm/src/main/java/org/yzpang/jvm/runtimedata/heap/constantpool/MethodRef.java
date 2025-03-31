package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantMemberRefInfo;
import org.yzpang.jvm.classfile.constantpool.ConstantMethodRefInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMemberRef;

public class MethodRef extends CustomMemberRef {
    public MethodRef(CustomConstantPool constantPool, ConstantMethodRefInfo methodRefInfo) {
        this.constantPool = constantPool;
        this.copyMemberRefInfo(methodRefInfo);
    }
}
