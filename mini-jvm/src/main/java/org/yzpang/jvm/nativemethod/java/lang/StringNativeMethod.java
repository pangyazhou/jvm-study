package org.yzpang.jvm.nativemethod.java.lang;

import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.CustomStringPool;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: String 类本地方法
 * Date: 2025/4/10 上午9:59
 **/
public class StringNativeMethod {

    public static void init () {
        NativeMethodRegistry.register(ClassConstants.STRING_CLASS, "intern",
                "()Ljava/lang/String;", getInternNativeMethod());
    }

    public static NativeMethod getInternNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                CustomObject thisObj = frame.getLocalVariable().getThis();
                CustomObject internedString = CustomStringPool.internedString(thisObj);
                frame.getOperandStack().pushReference(internedString);
            }
        };
    }
}
