package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: aload_2
 * 0x2c
 * Date: 2025/3/25 下午3:41
 **/
public class ALoad2Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        aload(frame, 2);
    }
}
