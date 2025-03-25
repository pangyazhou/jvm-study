package org.yzpang.jvm.instructions.stack;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: pop2
 * 弹出两个slot
 * Date: 2025/3/25 下午4:30
 **/
public class Pop2StackInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        operandStack.popSlot();
        operandStack.popSlot();
    }
}
