package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantMethodRefInfo;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.util.LookupUtil;

public class MethodRef extends CustomMemberRef implements CustomConstant {
    private CustomMethod method;

    public MethodRef(CustomConstantPool constantPool, ConstantMethodRefInfo methodRefInfo) {
        this.constantPool = constantPool;
        this.copyMemberRefInfo(methodRefInfo);
    }

    /**
     * 获取方法引用解析的方法结构体
     * @return method
     */
    public CustomMethod resolvedMethod() throws Exception {
        if (method == null) {
            resolveMethodRef();
        }
        return method;
    }

    /**
     * 解析方法引用
     */
    private void resolveMethodRef() throws Exception {
        CustomClass d = this.constantPool.getClazz();
        // c 声明方法的类
        CustomClass c = this.resolvedClass();
        // 非接口才能声明调用
        if (c.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        CustomMethod lookupMethod = lookupMethod(c, this.name, this.descriptor);
        if (lookupMethod == null) {
            throw new NoSuchMethodError();
        }
        // d 是否有权限访问 method
        if (!lookupMethod.isAccessibleTo(d)) {
            throw new IllegalAccessException();
        }
        this.method = lookupMethod;
    }

    /**
     * 在类结构体中查找方法
     * @param clazz 方法所在类
     * @param name 方法名
     * @param descriptor 方法描述符
     * @return method
     */
    private CustomMethod lookupMethod(CustomClass clazz, String name, String descriptor) {
        // 1.从类及父类中查找
        CustomMethod lookupMethod = LookupUtil.lookupMethodInClass(clazz, name, descriptor);
        // 2.从接口中查找
        if (lookupMethod == null) {
            lookupMethod = LookupUtil.lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
        }
        return lookupMethod;
    }
}
