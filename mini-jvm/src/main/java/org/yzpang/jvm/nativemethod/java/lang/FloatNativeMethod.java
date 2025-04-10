package org.yzpang.jvm.nativemethod.java.lang;

import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: Float类的本地方法
 * Date: 2025/4/10 上午9:43
 **/
public class FloatNativeMethod {

    public static void init() {
        NativeMethodRegistry.register(ClassConstants.FLOAT_CLASS, "floatToRawIntBits",
                "(F)I", getFloatToRawIntBitsNativeMethod());
        NativeMethodRegistry.register(ClassConstants.FLOAT_CLASS, "intBitsToFloat",
                "(I)F", getIntBitsToFloatNativeMethod());
    }


    public static NativeMethod getFloatToRawIntBitsNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                float value = frame.getLocalVariable().getFloat(0);
                int bits = Float.floatToRawIntBits(value);
                frame.getOperandStack().pushInt(bits);
            }
        };
    }

    public static NativeMethod getIntBitsToFloatNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                int bits = frame.getLocalVariable().getInt(0);
                float value = Float.intBitsToFloat(bits);
                frame.getOperandStack().pushFloat(value);
            }
        };
    }
}
