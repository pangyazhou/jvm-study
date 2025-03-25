package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: fmul
 * Date: 2025/3/25 下午5:06
 **/
public class FMulMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        float var1 = operandStack.popFloat();
        float var2 = operandStack.popFloat();
        float result = var2 * var1;
        operandStack.pushFloat(result);
    }
}
