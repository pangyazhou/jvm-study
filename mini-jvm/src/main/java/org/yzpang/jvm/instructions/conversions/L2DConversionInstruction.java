package org.yzpang.jvm.instructions.conversions;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * l2d
 * long-->double
 */
public class L2DConversionInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        long value = operandStack.popLong();
        double result = (double) value;
        operandStack.pushDouble(result);
    }
}
