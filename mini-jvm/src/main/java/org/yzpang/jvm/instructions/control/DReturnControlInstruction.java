package org.yzpang.jvm.instructions.control;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

/**
 * Author: yzpang
 * Desc: dreturn 0xaf
 * 从当前方法返回 double
 * Date: 2025/4/2 下午4:22
 **/
public class DReturnControlInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomThread thread = frame.getThread();
        // 当前帧
        CustomFrame currentFrame = thread.popFrame();
        // 调用此方法的方法帧
        CustomFrame invokerFrame = thread.topFrame();
        // 从当前帧的操作数栈弹出返回值 double
        double returnValue = currentFrame.getOperandStack().popDouble();
        // 将返回值压入调用者帧的操作数栈 double
        invokerFrame.getOperandStack().pushDouble(returnValue);
    }
}
