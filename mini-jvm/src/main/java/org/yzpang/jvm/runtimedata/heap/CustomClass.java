package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.classfile.ClassFile;
import org.yzpang.jvm.constant.ClassAccessConstants;
import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.runtimedata.CustomSlots;
import org.yzpang.jvm.runtimedata.util.ClassNameHelper;

import java.util.Objects;

/**
 * 自定义Class文件
 */
@Data
public class CustomClass {
    protected int accessFlags;
    protected String name;
    protected String superClassName;
    protected String[] interfaceNames;
    protected CustomConstantPool constantPool;
    protected CustomField[] fields;
    protected CustomMethod[] methods;
    protected CustomAttribute[] attributes;
    protected CustomClassLoader classloader;
    protected CustomClass superClass;
    protected CustomClass[] interfaces;
    protected int instanceSlotCount;
    protected int staticSlotCount;
    protected CustomSlots staticVariables;
    // 是否类初始化<clinit>
    protected boolean initialized = false;
    // java/lang/Class实例
    protected CustomObject jClass;

    public CustomClass() {
    }

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

    public boolean isSuperInterfaceOf(CustomClass otherClass) {
        return otherClass.isSubInterfaceOf(this);
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
    public boolean isAssignableFrom(CustomClass other) throws Exception {
        if (other == this){
            return true;
        }
        if (!other.isArray()) {
            if (!other.isInterface()) {
                if (!this.isInterface()) {
                    // 非接口则为此类的子类
                    return other.isSubClassOf(this);
                }  else {
                    // 接口为此接口的实现
                    return other.isImplements(this);
                }
            } else {
                if (!this.isInterface()) {
                    // 接口是Object类的子类
                    return this.isJ1Object();
                } else {
                    // 接口的子类
                    return this.isSuperInterfaceOf(other);
                }
            }
        } else {
            if (!this.isArray()) {
                // 数组可以转换为Object类/Cloneable接口/Serializable接口
                if (!this.isInterface()) {
                    return this.isJ1Object();
                } else {
                    return this.isJ1Cloneable() || this.isJ1Serializable();
                }
            } else {
                CustomClass otherComponentClass = ((CustomArrayClass) other).getComponentClass();
                CustomClass thisComponentClass = ((CustomArrayClass) this).getComponentClass();
                return otherComponentClass == thisComponentClass || thisComponentClass.isAssignableFrom(otherComponentClass);
            }
        }
    }

    /**
     * 类型是否是数组
     * 判断方式为类名第一个字符是否为 '['
     */
    protected boolean isArray(){
        return this.name.startsWith("[");
    }

    /**
     * 判断该类是否为Object
     * @return bool
     */
    protected boolean isJ1Object() {
        return this.name.equals(ClassConstants.OBJECT_CLASS);
    }

    /**
     * 判断该类是否为Cloneable接口
     * @return bool
     */
    protected boolean isJ1Cloneable(){
        return this.name.equals(ClassConstants.CLONEABLE_CLASS);
    }

    /**
     * 判断该类是否为Serializable接口
     * @return bool
     */
    protected boolean isJ1Serializable(){
        return this.name.equals(ClassConstants.SERIALIZABLE_CLASS);
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

    public String getJavaName() {
        return this.name.replaceAll("/", ".");
    }

    public boolean initStarted(){
        return this.initialized;
    }
    /**
     * 设置类初始化状态
     */
    public void startInit(){
        this.initialized = true;
    }

    /**
     * 根据字段名和描述符查找字段
     * @param name 字段名
     * @param descriptor 描述符
     * @param isStatic 是否静态字段
     * @return field
     */
    public CustomField getField(String name, String descriptor, boolean isStatic) {
        for (CustomClass c = this; c != null; c = c.getSuperClass()) {
            for (CustomField field : c.fields) {
                if (field.isStatic() == isStatic
                        &&field.getName().equals(name)
                        && field.getDescriptor().equals(descriptor)) {
                    return field;
                }
            }
        }
        return null;
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
     * 返回类初始化方法
     * @return method
     */
    public CustomMethod getClinitMethod() {
        return getStaticMethod("<clinit>", "()V");
    }

    /**
     * 返回主方法对象
     * @return method
     */
    public CustomMethod getMainMethod(){
        return getStaticMethod("main", "([Ljava/lang/String;)V");
    }

    /**
     * 返回数组类结构
     * @return arrayClass
     */
    public CustomArrayClass getArrayClass() throws Exception {
        String arrayClassName = ClassNameHelper.getArrayClassName(this.name);
        return (CustomArrayClass) classloader.loadClass(arrayClassName);
    }
}
