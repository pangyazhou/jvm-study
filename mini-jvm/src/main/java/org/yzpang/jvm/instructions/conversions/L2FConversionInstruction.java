package org.yzpang.jvm.instructions.conversions;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * l2f
 * long--float
 */
public class L2FConversionInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        long value = operandStack.popLong();
        float result = (float) value;
        operandStack.pushFloat(result);
    }
}
