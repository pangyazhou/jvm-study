package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * lor
 * 0x81
 */
public class LOrMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        long var1 = operandStack.popLong();
        long var2 = operandStack.popLong();
        long result = var2 | var1;
        operandStack.pushLong(result);
    }
}
