package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: fstore_2
 * 0x45
 * Date: 2025/3/25 下午3:54
 **/
public class FStore2Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        fstore(frame, 2);
    }
}
