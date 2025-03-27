package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: lload_1
 * 0x1f
 * Date: 2025/3/25 下午3:41
 **/
public class LLoad1Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        lload(frame, 1);
    }
}
