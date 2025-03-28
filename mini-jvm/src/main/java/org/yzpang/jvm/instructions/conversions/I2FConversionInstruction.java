package org.yzpang.jvm.instructions.conversions;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * i2f
 * 0x86
 * int-->float
 */
public class I2FConversionInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        int value = operandStack.popInt();
        float result =  (float) value;
        operandStack.pushFloat(result);
    }
}
