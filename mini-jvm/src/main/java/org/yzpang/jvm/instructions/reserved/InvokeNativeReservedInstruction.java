package org.yzpang.jvm.instructions.reserved;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.nativemethod.NativeMethod;
import org.yzpang.jvm.nativemethod.NativeMethodRegistry;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: invoke_native 0xfe
 * 保留指令,这里用来实现本地方法调用
 * Date: 2025/4/8 上午9:02
 **/
public class InvokeNativeReservedInstruction extends NoOperandsInstruction {
    static {
        NativeMethodRegistry.init();
    }

    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomMethod method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        String methodDescriptor = method.getDescriptor();
        NativeMethod nativeMethod = NativeMethodRegistry.findNativeMethod(className, methodName, methodDescriptor);
        if (nativeMethod == null) {
            String methodInfo = className + "." + methodName + methodDescriptor;
            throw new UnsatisfiedLinkError("未查询到注册的本地方法: " + methodInfo);
        }
        nativeMethod.invokeNativeMethod(frame);
    }
}
