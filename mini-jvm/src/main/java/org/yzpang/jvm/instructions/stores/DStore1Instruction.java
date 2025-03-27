package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: dstore_1
 * 0x48
 * Date: 2025/3/25 下午3:54
 **/
public class DStore1Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        dstore(frame, 1);
    }
}
