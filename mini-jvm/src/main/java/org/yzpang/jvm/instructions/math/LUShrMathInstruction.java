package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: lushr
 * 逻辑右位移
 * Date: 2025/3/25 下午5:24
 **/
public class LUShrMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        // 位移位数
        int var1 = operandStack.popInt();
        // 进行位移操作的变量
        long var2 = operandStack.popLong();
        // long类型64位, 位移位数只取前6个比特
        long result = var2 >>> (var1 & 0x3f);
        operandStack.pushLong(result);
    }
}
