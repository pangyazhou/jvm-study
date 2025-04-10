package org.yzpang.jvm.instructions.stack;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.CustomSlot;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: dup_x1
 * 0x5a
 * Before: | ... | B | A |
 * After:  | ... | A | B | A |
 * Date: 2025/3/25 下午4:34
 **/
public class DupX1StackInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomSlot slot1 = operandStack.popSlot();
        CustomSlot slot2 = operandStack.popSlot();
        operandStack.pushSlot(slot1.clone());
        operandStack.pushSlot(slot2);
        operandStack.pushSlot(slot1);
    }
}
