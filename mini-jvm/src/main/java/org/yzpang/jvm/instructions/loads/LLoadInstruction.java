package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.Index8Instruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: iload指令
 * 0x16
 * Date: 2025/3/25 下午3:40
 **/
public class LLoadInstruction extends Index8Instruction {
    @Override
    public void execute(CustomFrame frame) {
        lload(frame, this.index);
    }

}
