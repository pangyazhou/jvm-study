package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: dload_3
 * Date: 2025/3/25 下午3:41
 **/
public class DLoad3Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        dload(frame, 3);
    }
}
