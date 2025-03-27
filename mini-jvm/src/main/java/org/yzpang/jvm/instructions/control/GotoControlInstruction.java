package org.yzpang.jvm.instructions.control;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * goto
 * 0xa7
 * 无条件跳转
 */
public class GotoControlInstruction extends BranchInstruction {
    @Override
    public void execute(CustomFrame frame) {
        branch(frame, this.offset);
    }
}
