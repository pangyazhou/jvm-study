package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: fload_3
 * 0x25
 * Date: 2025/3/25 下午3:41
 **/
public class FLoad3Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        fload(frame, 3);
    }
}
