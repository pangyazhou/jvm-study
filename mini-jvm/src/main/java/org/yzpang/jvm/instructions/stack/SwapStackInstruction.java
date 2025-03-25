package org.yzpang.jvm.instructions.stack;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;
import org.yzpang.jvm.runtimedata.thread.CustomSlot;

/**
 * Author: yzpang
 * Desc: swap
 * Before: | ... | B | A |
 * After:  | ... | A | B |
 * Date: 2025/3/25 下午4:54
 **/
public class SwapStackInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomSlot slot1 = operandStack.popSlot();
        CustomSlot slot2 = operandStack.popSlot();
        operandStack.pushSlot(slot1);
        operandStack.pushSlot(slot2);
    }
}
