package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.classfile.constantpool.ConstantMemberRefInfo;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.util.LookupUtil;

public class InterfaceMethodRef extends CustomMemberRef implements CustomConstant {
    private CustomMethod method;

    public InterfaceMethodRef(CustomConstantPool constantPool, ConstantMemberRefInfo memberRefInfo) {
        this.constantPool = constantPool;
        this.copyMemberRefInfo(memberRefInfo);
    }

    /**
     * 返回方法结构体
     * @return method
     */
    public CustomMethod resolvedInterfaceMethod() throws Exception {
        if (method == null) {
            resolveInterfaceMethodRef();
        }
        return method;
    }

    /**
     * 根据常量池中的接口方法引用解析方法结构体
     */
    private void resolveInterfaceMethodRef() throws Exception {
        CustomClass d = this.constantPool.getClazz();
        // c 声明方法的类
        CustomClass c = this.resolvedClass();
        // 接口才能声明调用
        if (!c.isInterface()) {
            throw new IncompatibleClassChangeError();
        }
        CustomMethod lookupMethod = lookupInterfaceMethod(c, this.name, this.descriptor);
        if (lookupMethod == null) {
            throw new NoSuchMethodError();
        }
        // d 是否有权限访问 method
        if (!lookupMethod.isAccessibleTo(d)) {
            throw new IllegalAccessException();
        }
        this.method = lookupMethod;
    }

    private CustomMethod lookupInterfaceMethod(CustomClass clazz, String name, String descriptor) throws Exception {
        for (CustomMethod method : clazz.getMethods()) {
            if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                return method;
            }
        }
        return LookupUtil.lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
    }
}
