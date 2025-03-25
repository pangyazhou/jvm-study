package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: ddiv
 * Date: 2025/3/25 下午5:06
 **/
public class DDivMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        double var1 = operandStack.popDouble();
        double var2 = operandStack.popDouble();
        if (var1 == 0) {
            throw new ArithmeticException("被除数为0");
        }
        double result = var2 / var1;
        operandStack.pushDouble(result);
    }
}
