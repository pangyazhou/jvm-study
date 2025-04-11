package org.yzpang.jvm.nativemethod;

import org.yzpang.jvm.nativemethod.java.lang.*;
import org.yzpang.jvm.nativemethod.sun.misc.VMNativeMethod;

import java.util.HashMap;
import java.util.Map;

/**
 * Author: yzpang
 * Desc: 本地方法注册中心
 * Date: 2025/4/8 上午8:36
 **/
public class NativeMethodRegistry {
    /**
    * 本地方法注册信息
    * 所有的本地方法都以key-value形式保存到此Map中
     * JVM调用本地方法时根据key从此Map查找到响应的本地方法对象,调用执行逻辑,完成本地方法调用
     * key: className~methodName~methodDescriptor    value: NativeMethod对象
    * */
    public static final Map<String, NativeMethod> registry = new HashMap<>();

    /**
     * 注册本地方法
     * @param className 类名
     * @param methodName 方法名
     * @param methodDescriptor 方法描述符
     * @param method 注册的本地方法
     */
    public static void register(String className, String methodName, String methodDescriptor, NativeMethod method) {
        String key = className + "~" + methodName + "~" + methodDescriptor;
        registry.put(key, method);
    }

    /**
     * 查找本地方法
     * @param className 类名
     * @param methodName 方法名
     * @param methodDescriptor 方法描述符
     * @return 查找到的本地方法
     */
    public static NativeMethod findNativeMethod(String className, String methodName, String methodDescriptor) {
        if (methodName.equals("registerNatives") && methodDescriptor.equals("()V")) {
            return new EmptyNativeMethod();
        }
        String key = className + "~" + methodName + "~" + methodDescriptor;
        return registry.get(key);
    }

    /**
     * 初始化执行本地方法注册
     */
    public static void init() {
        ObjectNativeMethod.init();
        ClassNativeMethod.init();
        SystemNativeMethod.init();
        FloatNativeMethod.init();
        DoubleNativeMethod.init();
        StringNativeMethod.init();
        VMNativeMethod.init();
        ThrowableNativeMethod.init();
    }
}
