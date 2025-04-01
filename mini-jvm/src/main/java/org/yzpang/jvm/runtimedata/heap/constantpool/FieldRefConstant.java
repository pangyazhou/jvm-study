package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantFieldRefInfo;
import org.yzpang.jvm.runtimedata.heap.*;

public class FieldRefConstant extends CustomMemberRef implements CustomConstant<CustomField> {
    private CustomField field;

    public FieldRefConstant(CustomConstantPool customConstantPool, ConstantFieldRefInfo fieldRefInfo) {
        this.constantPool = customConstantPool;
        this.copyMemberRefInfo(fieldRefInfo);
    }

    public CustomField resolvedField() throws Exception {
        if (field == null) {
            resolveFieldRef();
        }
        return field;
    }

    /**
     * 解析字段引用
     */
    public void resolveFieldRef() throws Exception {
        CustomClass d = this.constantPool.getClazz();
        CustomClass c = resolvedClass();
        CustomField lookupField = lookupField(c, this.name, this.descriptor);
        if (lookupField == null) {
            throw new NoSuchFieldError(this.name);
        }
        if (!lookupField.isAccessibleTo(d)){
            throw new IllegalAccessException();
        }
        this.field = lookupField;
    }


    /**
     * 在类结构中根据Name和descriptor查找字段
     * @param clazz 字段所在类结构
     * @param name 字段名
     * @param descriptor 字段描述符
     * @return 查找到的字段对象
     */
    public CustomField lookupField(CustomClass clazz, String name, String descriptor) {
        // 1.先从本类中查找
        for (CustomField field : clazz.getFields()) {
            if (field.getName().equals(name) && field.getDescriptor().equals(descriptor)) {
                return field;
            }
        }
        // 2.从实现的接口中查找
        for (CustomClass interfaceClazz : clazz.getInterfaces()) {
            CustomField lookupField = lookupField(interfaceClazz, name, descriptor);
            if (lookupField != null){
                return lookupField;
            }
        }
        // 3.最后从父类中查找
        if (clazz.getSuperClass() != null) {
            return lookupField(clazz.getSuperClass(), name, descriptor);
        }
        return null;
    }

    @Override
    public CustomField get() {
        return this.field;
    }
}
