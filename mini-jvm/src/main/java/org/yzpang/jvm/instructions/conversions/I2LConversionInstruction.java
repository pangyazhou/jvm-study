package org.yzpang.jvm.instructions.conversions;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * i2l
 * int-->long
 */
public class I2LConversionInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        int value = operandStack.popInt();
        operandStack.pushLong(value);
    }
}
