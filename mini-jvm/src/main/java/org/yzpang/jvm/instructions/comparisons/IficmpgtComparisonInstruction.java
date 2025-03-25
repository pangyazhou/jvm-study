package org.yzpang.jvm.instructions.comparisons;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * if_icmpgt
 * 此指令将操作数栈顶的两个int变量弹出, 然后进行比较, 满足条件则跳转.
 */
public class IficmpgtComparisonInstruction extends BranchInstruction {
    @Override
    public void execute(CustomFrame frame) {
        int var1 = frame.getOperandStack().popInt();
        int var2 = frame.getOperandStack().popInt();
        if (var2 > var1){
            branch(frame, this.offset);
        }
    }
}
