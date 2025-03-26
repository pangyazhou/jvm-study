package org.yzpang.jvm.instructions.base;

import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: 跳转指令
 * Date: 2025/3/25 下午2:29
 **/
public class BranchInstruction extends NoOperandsInstruction {
    // 跳转偏移量
    protected int offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readShort();
    }

    protected void branch(CustomFrame frame, int offset){
        int pc = frame.getThread().getPc();
        int nextPC = pc + offset;
        frame.setNextPC(nextPC);
    }

}
