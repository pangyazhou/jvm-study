package org.yzpang.jvm.instructions.extended;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * ifnull 跳转
 * 0xc6
 */
public class IfNullExtendedInstruction extends BranchInstruction {
    @Override
    public void execute(CustomFrame frame) {
        Object reference = frame.getOperandStack().popReference();
        if (reference == null){
            branch(frame, offset);
        }
    }
}
