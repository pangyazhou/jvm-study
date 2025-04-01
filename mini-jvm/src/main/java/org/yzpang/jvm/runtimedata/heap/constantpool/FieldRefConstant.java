package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantFieldRefInfo;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomField;
import org.yzpang.jvm.runtimedata.heap.CustomMemberRef;

public class FieldRefConstant extends CustomMemberRef implements CustomConstant<Object> {
    private CustomField field;

    public FieldRefConstant(CustomConstantPool customConstantPool, ConstantFieldRefInfo fieldRefInfo) {
        this.constantPool = customConstantPool;
        this.copyMemberRefInfo(fieldRefInfo);
    }

    @Override
    public Object get() {
        return null;
    }
}
