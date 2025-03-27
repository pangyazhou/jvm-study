package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: istore_0
 * 0x3b
 * Date: 2025/3/25 下午3:54
 **/
public class IStore0Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        istore(frame, 0);
    }
}
