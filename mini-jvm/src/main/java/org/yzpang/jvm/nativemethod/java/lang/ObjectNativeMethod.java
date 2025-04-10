package org.yzpang.jvm.nativemethod.java.lang;

import cn.hutool.core.util.RandomUtil;
import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

import java.util.Random;

/**
 * Author: yzpang
 * Desc: 构建Object类本地方法
 * Date: 2025/4/8 上午10:26
 **/
public class ObjectNativeMethod {
    public static void init()  {
        // 注册getClass()方法
        NativeMethodRegistry.register(ClassConstants.OBJECT_CLASS, "getClass", "()Ljava/lang/Class;", getClassNativeMethod());
        NativeMethodRegistry.register(ClassConstants.OBJECT_CLASS, "hashCode", "()I", getHashCodeNativeMethod());
    }

    // public final native Class<?> getClass();
    public static NativeMethod getClassNativeMethod(){
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) {
                // 对象引用
                CustomObject thisObj = frame.getLocalVariable().getThis();
                // 对象所属类的类对象
                CustomObject jClass = thisObj.getClazz().getJClass();
                // areturn
                frame.getOperandStack().pushReference(jClass);
            }
        };
    }

    // public native int hashCode();
    public static NativeMethod getHashCodeNativeMethod(){
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                CustomObject thisObj = frame.getLocalVariable().getThis();
                int hashCode = thisObj.getHashCode();
                if (hashCode == 0) {
                    hashCode = RandomUtil.randomInt(1, Integer.MAX_VALUE);
                    thisObj.setHashCode(hashCode);
                }
                frame.getOperandStack().pushInt(hashCode);
            }
        };
    }
}
