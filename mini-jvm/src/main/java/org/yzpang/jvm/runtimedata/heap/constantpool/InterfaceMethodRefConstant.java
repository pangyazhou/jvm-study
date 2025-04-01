package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantMemberRefInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMemberRef;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;

public class InterfaceMethodRefConstant extends CustomMemberRef implements CustomConstant {
    private CustomMethod method;

    public InterfaceMethodRefConstant(CustomConstantPool constantPool, ConstantMemberRefInfo memberRefInfo) {
        this.constantPool = constantPool;
        this.copyMemberRefInfo(memberRefInfo);
    }
}
