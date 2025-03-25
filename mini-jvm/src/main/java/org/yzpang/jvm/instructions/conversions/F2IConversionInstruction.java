package org.yzpang.jvm.instructions.conversions;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * f2i
 * float-->int
 */
public class F2IConversionInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        float value = operandStack.popFloat();
        int result = (int) value;
        operandStack.pushInt(result);
    }
}
