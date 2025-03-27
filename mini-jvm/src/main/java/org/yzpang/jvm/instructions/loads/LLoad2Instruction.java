package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: lload_2
 * 0x20
 * Date: 2025/3/25 下午3:41
 **/
public class LLoad2Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        lload(frame, 2);
    }
}
