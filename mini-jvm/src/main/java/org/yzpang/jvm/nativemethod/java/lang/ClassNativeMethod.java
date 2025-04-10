package org.yzpang.jvm.nativemethod.java.lang;

import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomClassLoader;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.CustomStringPool;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: 构建Class的本地方法
 * Date: 2025/4/8 上午10:33
 **/
public class ClassNativeMethod {

    public static void init()  {
        NativeMethodRegistry.register(ClassConstants.CLASS_CLASS, "getPrimitiveClass",
                "(Ljava/lang/String;)Ljava/lang/Class;",
                getPrimitiveClassNativeMethod());
        NativeMethodRegistry.register(ClassConstants.CLASS_CLASS, "getName0",
                "()Ljava/lang/String;", getName0NativeMethod());
        NativeMethodRegistry.register(ClassConstants.CLASS_CLASS, "desiredAssertionStatus0",
                "(Ljava/lang/Class;)Z", getDesiredAssertionStatus0NativeMethod());
    }

    // static native Class<?> getPrimitiveClass(String name);
    private static NativeMethod getPrimitiveClassNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                CustomObject nameObj = frame.getLocalVariable().getThis();
                String name = CustomStringPool.goString(nameObj);
                CustomClassLoader classloader = frame.getMethod().getClazz().getClassloader();
                CustomObject jClass = classloader.loadClass(name).getJClass();
                frame.getOperandStack().pushReference(jClass);
            }
        };
    }

    // private native String getName0();
    private static NativeMethod getName0NativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                CustomObject thisObj = frame.getLocalVariable().getThis();
                CustomClass extraClazz = thisObj.getExtra();
                String name = extraClazz.getJavaName();
                CustomObject nameObj = CustomStringPool.jString(extraClazz.getClassloader(), name);
                frame.getOperandStack().pushReference(nameObj);
            }
        };
    }

    // private static native boolean desiredAssertionStatus0(Class<?> clazz);
    private static NativeMethod getDesiredAssertionStatus0NativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) {
                frame.getOperandStack().pushBoolean(false);
            }
        };
    }
}
