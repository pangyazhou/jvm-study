package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * ixor
 * 0x82
 */
public class IXorMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        int var1 = operandStack.popInt();
        int var2 = operandStack.popInt();
        int result = var2 ^ var1;
        operandStack.pushInt(result);
    }
}
