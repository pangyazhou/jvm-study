package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.Index8Instruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: fload指令
 * Date: 2025/3/25 下午3:40
 **/
public class FLoadInstruction extends Index8Instruction {
    @Override
    public void execute(CustomFrame frame) {
        fload(frame, this.index);
    }

}
