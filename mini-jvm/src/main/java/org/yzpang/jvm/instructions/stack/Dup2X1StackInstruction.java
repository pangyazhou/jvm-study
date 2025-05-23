package org.yzpang.jvm.instructions.stack;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.CustomSlot;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: dup2_x1
 * 0x5d
 * Date: 2025/3/25 下午4:34
 **/
public class Dup2X1StackInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomSlot slot1 = operandStack.popSlot();
        CustomSlot slot2 = operandStack.popSlot();
        CustomSlot slot3 = operandStack.popSlot();
        operandStack.pushSlot(slot2.clone());
        operandStack.pushSlot(slot1.clone());
        operandStack.pushSlot(slot3);
        operandStack.pushSlot(slot2);
        operandStack.pushSlot(slot1);
    }
}
