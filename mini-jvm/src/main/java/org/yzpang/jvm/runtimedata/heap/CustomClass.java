package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.AttributeInfo;
import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.classfile.FieldInfo;
import org.yzpang.jvm.classfile.MethodInfo;
import org.yzpang.jvm.classfile.attribute.CodeAttribute;
import org.yzpang.jvm.classpath.CustomClassloader;
import org.yzpang.jvm.runtimedata.thread.CustomSlot;

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
    private AttributeInfo[] attributes;
    private CustomClassloader classloader;
    private CustomClass superClass;
    private CustomClass[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private CustomSlot staticVariable;

    public static CustomClass newInstance(ClassFile classFile) {
        CustomClass customClass = new CustomClass();
        customClass.setAccessFlags(classFile.getAccessFlags());
        customClass.setName(classFile.getClassName());
        customClass.setSuperClassName(classFile.getSuperClassName());
        customClass.setInterfaceNames(classFile.getInterfacesNames());

        customClass.setConstantPool(CustomConstantPool.newConstantPool(customClass, classFile.getConstantPoolInfos()));
        customClass.setFields(customClass.newFields(customClass, classFile.getFields()));
        customClass.setMethods(customClass.newMethods(customClass, classFile.getMethods()));
        return customClass;
    }


    private CustomMethod[] newMethods(CustomClass clazz, MethodInfo[] methodInfos){
        CustomMethod[] methods = new CustomMethod[methodInfos.length];
        for(int i = 0; i < methodInfos.length; i++){
            methods[i] = new CustomMethod();
            methods[i].setAccessFlags(methodInfos[i].getAccessFlags());
            methods[i].setClazz(clazz);
            methods[i].setName(methodInfos[i].getName());
            methods[i].setDescriptor(methodInfos[i].getDescriptor());
            CodeAttribute codeAttribute = methodInfos[i].getCodeAttribute();
            methods[i].setCode(codeAttribute.getCode());
            methods[i].setMaxLocal(codeAttribute.getMaxLocals());
            methods[i].setMaxStack(codeAttribute.getMaxStack());
        }
        return methods;
    }

    private CustomField[] newFields(CustomClass clazz, FieldInfo[] fieldInfos) {
        CustomField[] fields = new CustomField[fieldInfos.length];
        for (int i = 0; i < fieldInfos.length; i++) {
            fields[i] = new CustomField();
            fields[i].setClazz(clazz);
            fields[i].setName(fieldInfos[i].getName());
            fields[i].setDescriptor(fieldInfos[i].getDescriptor());
            fields[i].setAccessFlags(fieldInfos[i].getAccessFlags());
        }
        return fields;
    }
}
