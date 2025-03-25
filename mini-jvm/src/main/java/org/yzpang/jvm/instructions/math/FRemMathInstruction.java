package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: frem
 * Date: 2025/3/25 下午4:57
 **/
public class FRemMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        float var1 = operandStack.popFloat();
        float var2 = operandStack.popFloat();
        float rem = var2 / var1;
        operandStack.pushFloat(var1);
    }
}
