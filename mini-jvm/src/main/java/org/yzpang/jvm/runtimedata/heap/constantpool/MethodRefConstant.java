package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantMethodRefInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMemberRef;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;

public class MethodRefConstant extends CustomMemberRef implements CustomConstant {
    private CustomMethod method;

    public MethodRefConstant(CustomConstantPool constantPool, ConstantMethodRefInfo methodRefInfo) {
        this.constantPool = constantPool;
        this.copyMemberRefInfo(methodRefInfo);
    }
}
