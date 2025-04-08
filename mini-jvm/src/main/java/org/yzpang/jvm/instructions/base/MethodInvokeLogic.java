package org.yzpang.jvm.instructions.base;

import org.yzpang.jvm.runtimedata.CustomSlot;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

/**
 * Author: yzpang
 * Desc: 通用方法调用逻辑
 * Date: 2025/4/2 下午4:14
 **/
public class MethodInvokeLogic {

    /**
     * 通用方法调用, 压入新方法生成的栈帧, 并传递参数
     * @param frame 当前栈帧
     * @param method 调用的方法
     */
    public static void invokeMethod(CustomFrame frame, CustomMethod method) {
        // 当前线程
        CustomThread currentThread = frame.getThread();
        CustomFrame methodFrame = currentThread.newFrame(method);
        currentThread.pushFrame(methodFrame);

        // 参数传递, 从frame帧的操作数栈弹出到methodFrame帧的局部变量表
        int argSlotCount = method.getArgSlotCount();
        if (argSlotCount > 0) {
            for (int i = argSlotCount - 1; i >= 0; i--) {
                CustomSlot slot = frame.getOperandStack().popSlot();
                methodFrame.getLocalVariable().setSlot(i, slot);
            }
        }
    }
}
