package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: drem
 * Date: 2025/3/25 下午4:57
 **/
public class DRemMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        double var1 = operandStack.popDouble();
        double var2 = operandStack.popDouble();
        double rem = var2 / var1;
        operandStack.pushDouble(var1);
    }
}
