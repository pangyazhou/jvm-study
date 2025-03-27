package org.yzpang.jvm.instructions.comparisons;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * if_acmpeq
 * 0xa5
 * 此指令将操作数栈顶的两个引用变量弹出, 然后进行比较, 满足条件则跳转.
 */
public class IfacmpeqComparisonInstruction extends BranchInstruction {
    @Override
    public void execute(CustomFrame frame) {
        Object var1 = frame.getOperandStack().popReference();
        Object var2 = frame.getOperandStack().popReference();
        if (var2 == var1){
            branch(frame, this.offset);
        }
    }
}
