package org.yzpang.jvm.instructions.stack;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;
import org.yzpang.jvm.runtimedata.CustomSlot;

/**
 * Author: yzpang
 * Desc: dup2
 * 0x5c
 * Before: | ... | B | A |
 * After:  | ... | B | A | B | A |
 * Date: 2025/3/25 下午4:34
 **/
public class Dup2StackInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomSlot slot1 = operandStack.popSlot();
        CustomSlot slot2 = operandStack.popSlot();
        operandStack.pushSlot(slot2);
        operandStack.pushSlot(slot1);
        operandStack.pushSlot(slot2.clone());
        operandStack.pushSlot(slot1.clone());
    }
}
