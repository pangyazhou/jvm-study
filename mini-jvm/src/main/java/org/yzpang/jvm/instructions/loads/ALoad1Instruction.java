package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: aload_1
 * 0x2b
 * Date: 2025/3/25 下午3:41
 **/
public class ALoad1Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        aload(frame, 1);
    }
}
