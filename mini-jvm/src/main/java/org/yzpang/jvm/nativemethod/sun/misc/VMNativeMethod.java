package org.yzpang.jvm.nativemethod.sun.misc;

import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.instructions.base.MethodInvokeLogic;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: VM 类的本地方法实现
 * Date: 2025/4/10 下午3:14
 **/
public class VMNativeMethod {
    private static final String CLASS_NAME = "sun/misc/VM";

    public static void init() {
        NativeMethodRegistry.register(CLASS_NAME, "initialize", "()V", getInitializeNativeMethod());
    }

    public static NativeMethod getInitializeNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                CustomClass clazz = frame.getMethod().getClazz();
                CustomObject savedProps = clazz.getRefVar("savedProps", "Ljava/util/Properties;");
                CustomObject key = CustomStringPool.jString(clazz.getClassloader(), "foo");
                CustomObject value = CustomStringPool.jString(clazz.getClassloader(), "bar");
                frame.getOperandStack().pushReference(savedProps);
                frame.getOperandStack().pushReference(key);
                frame.getOperandStack().pushReference(value);
                CustomClass propsClass = clazz.getClassloader().loadClass("java/util/Properties");
                CustomMethod setPropertyMethod = propsClass.getInstanceMethod("setProperty", "(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/Object;");
                MethodInvokeLogic.invokeMethod(frame, setPropertyMethod);
                /*CustomClassLoader classloader = frame.getMethod().getClazz().getClassloader();
                CustomClass systemClass = classloader.loadClass(ClassConstants.SYSTEM_CLASS);
                CustomMethod initializeSystemClass = systemClass.getStaticMethod("initializeSystemClass", "()V");
                MethodInvokeLogic.invokeMethod(frame, initializeSystemClass);*/
            }
        };
    }
}
