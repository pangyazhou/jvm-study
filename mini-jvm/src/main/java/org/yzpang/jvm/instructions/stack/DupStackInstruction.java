package org.yzpang.jvm.instructions.stack;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;
import org.yzpang.jvm.runtimedata.CustomSlot;

/**
 * Author: yzpang
 * Desc: dup
 * 0x59
 * Before: | ... | B | A |
 * After:  | ... | B | A | A |
 * Date: 2025/3/25 下午4:34
 **/
public class DupStackInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomSlot slot = operandStack.popSlot();
        operandStack.pushSlot(slot);
        operandStack.pushSlot(slot);
    }
}
