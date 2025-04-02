package org.yzpang.jvm.runtimedata.util;

import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;

/**
 * Author: yzpang
 * Desc: 查找引用
 * Date: 2025/4/2 下午3:01
 **/
public class LookupUtil {

    /**
     * 在类及父类中查找方法
     * @param clazz 方法所在类
     * @param name 方法名
     * @param descriptor 方法描述符
     * @return method
     */
    public static CustomMethod lookupMethodInClass(CustomClass clazz, String name, String descriptor) {
        for (CustomClass c = clazz; c != null; c = c.getSuperClass()) {
            for (CustomMethod method : c.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
        }
        return null;
    }

    /**
     * 从接口中查找方法
     * @param interfaces 接口数组
     * @param name 方法名
     * @param descriptor 描述符
     * @return method
     */
    public static CustomMethod lookupMethodInInterfaces(CustomClass[] interfaces, String name, String descriptor) {
        for (CustomClass clazz : interfaces) {
            for (CustomMethod method : clazz.getMethods()) {
                if (method.getName().equals(name) && method.getDescriptor().equals(descriptor)) {
                    return method;
                }
            }
            CustomMethod method = lookupMethodInInterfaces(clazz.getInterfaces(), name, descriptor);
            if (method != null) {
                return method;
            }
        }
        return null;
    }
}
