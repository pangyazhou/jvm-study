package org.yzpang.jvm.instructions.comparisons;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * lcmp
 * 比较long, 返回 -1, 0, 1
 */
public class LcmpComparisonInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        long var1 = operandStack.popLong();
        long var2 = operandStack.popLong();
        if (var2 > var1){
            operandStack.pushInt(1);
        } else if (var2 == var1){
            operandStack.pushInt(0);
        } else {
            operandStack.pushInt(-1);
        }
    }
}
