package org.yzpang.jvm.nativemethod.java.lang;

import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.CustomStackTraceElement;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.util.StackTraceUtil;

/**
 * Author: yzpang
 * Desc: 类Throwable的本地方法实现
 * Date: 2025/4/11 上午8:45
 **/
public class ThrowableNativeMethod {

    private static final String THROWABLE_CLASS = "java/lang/Throwable";

    public static void init() {
        NativeMethodRegistry.register(THROWABLE_CLASS, "fillInStackTrace", "(I)Ljava/lang/Throwable;", getFillInStackTraceNativeMethod());
    }


    // private native Throwable fillInStackTrace(int dummy)
    public static NativeMethod getFillInStackTraceNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                CustomObject thisObj = frame.getLocalVariable().getThis();
                frame.getOperandStack().pushReference(thisObj);
                CustomStackTraceElement[] stackTrace = StackTraceUtil.createStackTraceElements(thisObj, frame.getThread());
                thisObj.setStackTrace(stackTrace);
            }
        };
    }
}
