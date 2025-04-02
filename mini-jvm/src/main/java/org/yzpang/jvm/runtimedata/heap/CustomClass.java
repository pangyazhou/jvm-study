package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.constant.ClassAccessConstants;
import org.yzpang.jvm.runtimedata.CustomSlots;

import java.util.Objects;

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

    public CustomObject newObject(){
        return new CustomObject(this);
    }

    /**
     * 类是否可访问
     * 判断otherClass能否访问当前类
     * public 或者与另一个类在同一个包中
     */
    public boolean isAccessibleTo(CustomClass otherClass) {
        return this.isPublic() || Objects.equals(this.getPackageName(), otherClass.getPackageName());
    }

    /**
     * 判断当前类是否为otherClass的子类
     */
    public boolean isSubClassOf(CustomClass otherClass) {
        CustomClass c = this.superClass;
        while (c != null) {
            if (c == otherClass) {
                return true;
            }
            c = c.getSuperClass();
        }
        return false;
    }

    /**
     * 超类判断
     */
    public boolean isSuperClassOf(CustomClass otherClass) {
        return otherClass.isSubClassOf(this);
    }

    /**
     * 判断当前类是否实现了接口otherClass
     */
    public boolean isImplements(CustomClass otherClass) {
        CustomClass c = this;
        while (c != null) {
            for (CustomClass interfaceClazz : c.interfaces) {
                if (interfaceClazz == otherClass || interfaceClazz.isSubInterfaceOf(otherClass)) {
                    return true;
                }
            }
            c = c.getSuperClass();
        }
        return false;
    }

    /**
     * 判断当前接口是否继承了otherClass接口
     */
    public boolean isSubInterfaceOf(CustomClass otherClass) {
        for (CustomClass interfaceClazz : this.interfaces) {
            if (interfaceClazz == otherClass || interfaceClazz.isSubInterfaceOf(otherClass)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 当前类是否适配other
     * other 是当前类的子类或实现
     */
    public boolean isAssignableFrom(CustomClass other) {
        if (other == this){
            return true;
        }
        if (!isInterface()) {
            // 非接口则为此类的子类
            return other.isSubClassOf(this);
        } else {
            // 接口则为此接口的实现
            return other.isImplements(this);
        }
    }

    public boolean isSuper() {
        return (this.accessFlags & ClassAccessConstants.ACC_SUPER) != 0;
    }

    public boolean isInterface(){
        return (this.accessFlags & ClassAccessConstants.ACC_INTERFACE) != 0;
    }

    public boolean isAbstract(){
        return (this.accessFlags & ClassAccessConstants.ACC_ABSTRACT) != 0;
    }

    public boolean isPublic(){
        return (this.accessFlags & ClassAccessConstants.ACC_PUBLIC) != 0;
    }

    public boolean isProtected(){
        return (this.accessFlags & ClassAccessConstants.ACC_PROTECTED) != 0;
    }

    public boolean isPrivate(){
        return (this.accessFlags & ClassAccessConstants.ACC_PRIVATE) != 0;
    }

    public boolean isStatic(){
        return (this.accessFlags & ClassAccessConstants.ACC_STATIC) != 0;
    }

    public boolean isFinal(){
        return (this.accessFlags & ClassAccessConstants.ACC_FINAL) != 0;
    }

    public boolean isSynthetic(){
        return (this.accessFlags & ClassAccessConstants.ACC_SYNTHETIC) != 0;
    }

    public boolean isAnnotation() {
        return (this.accessFlags & ClassAccessConstants.ACC_ANNOTATION) != 0;
    }

    public boolean isEnum() {
        return (this.accessFlags & ClassAccessConstants.ACC_ENUM) != 0;
    }

    public String getPackageName() {
        // java/lang/String
        return this.name.substring(0, this.name.lastIndexOf("/"));
    }

    /**
     * 返回静态方法对象
     * @param name 方法名
     * @param descriptor 方法描述符
     * @return method
     */
    public CustomMethod getStaticMethod(String name, String descriptor) {
        for (CustomMethod method : this.methods) {
            if (method.isStatic() && method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 返回主方法对象
     * @return method
     */
    public CustomMethod getMainMethod(){
        return getStaticMethod("main", "([Ljava/lang/String;)V");
    }
}
