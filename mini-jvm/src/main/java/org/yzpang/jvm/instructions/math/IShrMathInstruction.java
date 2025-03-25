package org.yzpang.jvm.instructions.math;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: ishl
 * 算术左位移
 * Date: 2025/3/25 下午5:24
 **/
public class IShrMathInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        CustomOperandStack operandStack = frame.getOperandStack();
        // 位移位数
        int var1 = operandStack.popInt();
        // 进行位移操作的变量
        int var2 = operandStack.popInt();
        // int类型32位, 位移位数只取前5个比特
        int result = var2 << (var1 & 0x1f);
        operandStack.pushInt(result);
    }
}
