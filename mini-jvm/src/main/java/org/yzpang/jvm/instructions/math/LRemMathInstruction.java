package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: lrem
 * Date: 2025/3/25 下午4:57
 **/
public class LRemMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        long var1 = operandStack.popLong();
        long var2 = operandStack.popLong();
        if (var1 == 0){
            throw new ArithmeticException("除数为0");
        }
        long rem = var2 % var1;
        operandStack.pushLong(rem);
    }
}
