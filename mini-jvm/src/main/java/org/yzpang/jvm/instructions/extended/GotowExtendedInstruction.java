package org.yzpang.jvm.instructions.extended;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * goto_w
 * offset 为4字节
 */
public class GotowExtendedInstruction extends BranchInstruction {
    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readInt();
    }

    @Override
    public void execute(CustomFrame frame) {
        branch(frame, this.offset);
    }
}