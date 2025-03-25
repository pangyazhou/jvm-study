package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: astore_0
 * Date: 2025/3/25 下午3:54
 **/
public class AStore0Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        astore(frame, 0);
    }
}
