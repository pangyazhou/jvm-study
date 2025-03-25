package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: dadd
 * Date: 2025/3/25 下午5:06
 **/
public class DAddMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        double var1 = operandStack.popDouble();
        double var2 = operandStack.popDouble();
        double result = var1 + var2;
        operandStack.pushDouble(result);
    }
}
