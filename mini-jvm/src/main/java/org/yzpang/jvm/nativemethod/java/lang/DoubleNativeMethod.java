package org.yzpang.jvm.nativemethod.java.lang;

import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: Double类的本地方法
 * Date: 2025/4/10 上午9:43
 **/
public class DoubleNativeMethod {

    public static void init() {
        NativeMethodRegistry.register(ClassConstants.DOUBLE_CLASS, "doubleToRawLongBits",
                "(D)J", getDoubleToRawLongBitsNativeMethod());
        NativeMethodRegistry.register(ClassConstants.DOUBLE_CLASS, "longBitsToDouble",
                "(J)D", getLongBitsToDoubleNativeMethod());
    }


    public static NativeMethod getDoubleToRawLongBitsNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                double value = frame.getLocalVariable().getDouble(0);
                long bits = Double.doubleToRawLongBits(value);
                frame.getOperandStack().pushLong(bits);
            }
        };
    }

    public static NativeMethod getLongBitsToDoubleNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                long bits = frame.getLocalVariable().getLong(0);
                double value = Double.longBitsToDouble(bits);
                frame.getOperandStack().pushDouble(value);
            }
        };
    }
}
