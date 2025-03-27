package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: ftore_0
 * 0x43
 * Date: 2025/3/25 下午3:54
 **/
public class FStore0Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        fstore(frame, 0);
    }
}
