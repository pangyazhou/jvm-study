package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.*;
import org.yzpang.jvm.classfile.attribute.CodeAttribute;
import org.yzpang.jvm.classpath.CustomClassLoader;
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
    private CustomClassLoader classloader;
    private CustomClass superClass;
    private CustomClass[] interfaces;
    private int instanceSlotCount;
    private int staticSlotCount;
    private CustomSlot staticVariable;

    public CustomClass(ClassFile classFile) {
        this.accessFlags = classFile.getAccessFlags();
        this.name = classFile.getClassName();
        this.superClassName = classFile.getSuperClassName();
        this.interfaceNames = classFile.getInterfacesNames();

        this.constantPool =  new CustomConstantPool(this, classFile.getConstantPool());
        this.fields = newFields(this, classFile.getFields());
        this.methods = newMethods(this, classFile.getMethods());
        this.attributes = newAttributes(this, classFile.getAttributes());
    }


    private CustomMethod[] newMethods(CustomClass clazz, MemberInfo[] methodInfos){
        CustomMethod[] methods = new CustomMethod[methodInfos.length];
        for(int i = 0; i < methodInfos.length; i++){
            methods[i] = new CustomMethod();
            methods[i].setClazz(clazz);
            methods[i].copyMemberInfo(methodInfos[i]);
            methods[i].copyCodeAttribute(methodInfos[i]);
        }
        return methods;
    }

    private CustomField[] newFields(CustomClass clazz, MemberInfo[] fieldInfos) {
        CustomField[] fields = new CustomField[fieldInfos.length];
        for (int i = 0; i < fieldInfos.length; i++) {
            fields[i] = new CustomField();
            fields[i].setClazz(clazz);
            fields[i].copyMemberInfo(fieldInfos[i]);
        }
        return fields;
    }

    private AttributeInfo[] newAttributes(CustomClass clazz, AttributeInfo[] attributeInfos) {
        // todo
        return new AttributeInfo[attributeInfos.length];
    }
}
