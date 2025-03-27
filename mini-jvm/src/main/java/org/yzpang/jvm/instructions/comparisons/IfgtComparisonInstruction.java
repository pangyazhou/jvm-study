package org.yzpang.jvm.instructions.comparisons;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * ifgt
 * 0x9c
 * 此指令将操作数栈顶的int变量弹出, 然后跟0比较, 满足条件则跳转.
 */
public class IfgtComparisonInstruction extends BranchInstruction {
    @Override
    public void execute(CustomFrame frame) {
        int value = frame.getOperandStack().popInt();
        if (value > 0) {
            branch(frame, this.offset);
        }
    }
}
