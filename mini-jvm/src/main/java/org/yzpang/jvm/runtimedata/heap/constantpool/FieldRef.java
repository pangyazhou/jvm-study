package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantFieldRefInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMemberRef;

public class FieldRef extends CustomMemberRef {

    public FieldRef(CustomConstantPool customConstantPool, ConstantFieldRefInfo fieldRefInfo) {
        this.constantPool = customConstantPool;
        this.copyMemberRefInfo(fieldRefInfo);
    }
}
