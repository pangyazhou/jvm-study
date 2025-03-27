package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: lstore_0
 * 0x3f
 * Date: 2025/3/25 下午3:54
 **/
public class LStore0Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        lstore(frame, 0);
    }
}
