package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.runtimedata.CustomSlots;

/**
 * 自定义Class文件
 */
@Data
public class CustomClass {
    private int accessFlags;
    private String name;
    private String superClassName;
    private String[] interfaceNames;
    private CustomConstantPool constantPool;
    private CustomField[] fields;
    private CustomMethod[] methods;
    private CustomAttribute[] attributes;
    private CustomClassLoader classloader;
    private CustomClass superClass;
    private CustomClass[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private CustomSlots staticVariables;

    public CustomClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags();
        this.name = classFile.getClassName();
        this.superClassName = classFile.getSuperClassName();
        this.interfaceNames = classFile.getInterfacesNames();
        this.constantPool =  new CustomConstantPool(this, classFile.getConstantPool());
        this.fields = CustomField.newFields(this, classFile.getFields());
        this.methods = CustomMethod.newMethods(this, classFile.getMethods());
        this.attributes = CustomAttribute.newAttributes(this, classFile.getAttributes());
    }
}
