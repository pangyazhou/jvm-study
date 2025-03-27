package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: ladd
 * 0x61
 * Date: 2025/3/25 下午5:06
 **/
public class LAddMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        long var1 = operandStack.popLong();
        long var2 = operandStack.popLong();
        long result = var1 + var2;
        operandStack.pushLong(result);
    }
}
