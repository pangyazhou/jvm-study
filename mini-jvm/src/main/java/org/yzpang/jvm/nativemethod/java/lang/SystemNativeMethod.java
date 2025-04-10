package org.yzpang.jvm.nativemethod.java.lang;

import org.yzpang.jvm.constant.ClassConstants;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.heap.CustomArrayObject;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomLocalVariable;
import org.yzpang.jvm.runtimedata.util.ArrayUtil;

/**
 * Author: yzpang
 * Desc: System类的本地方法实现
 * Date: 2025/4/10 上午8:51
 **/
public class SystemNativeMethod {

    public static void init() {
        NativeMethodRegistry.register(ClassConstants.SYSTEM_CLASS, "arraycopy",
                "(Ljava/lang/Object;ILjava/lang/Object;II)V", getArrayCopyNativeMethod());
    }

    // public static native void arraycopy(Object src, int srcPos, Object dest, int destPos, int length)
    public static NativeMethod getArrayCopyNativeMethod() {
        return new NativeMethod() {
            @Override
            public void invokeNativeMethod(CustomFrame frame) throws Exception {
                // 读取参数
                CustomLocalVariable localVariable = frame.getLocalVariable();
                CustomArrayObject srcObj = (CustomArrayObject) localVariable.getReference(0);
                int srcPos = localVariable.getInt(1);
                CustomArrayObject destObj = (CustomArrayObject) localVariable.getReference(2);
                int destPos = localVariable.getInt(3);
                int length = localVariable.getInt(4);
                // 参数校验
                // 空指针校验
                if (srcObj == null || destObj == null) {
                    throw new NullPointerException("数组复制时数组对象为空");
                }
                // 数组兼容校验
                if (!ArrayUtil.checkArrayCopy(srcObj, destObj)) {
                    throw new ArrayStoreException("源数组与目标数组不兼容");
                }
                // 数组越界校验
                if (srcPos < 0 || destPos < 0 || length < 0 || srcPos + length > srcObj.arrayLength() || destPos + length > destObj.arrayLength()) {
                    throw new IndexOutOfBoundsException("数组复制时索引越界");
                }
                // 复制数组
                ArrayUtil.arrayCopy(srcObj, destObj, srcPos, destPos, length);
            }
        };
    }
}
