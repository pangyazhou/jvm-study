package org.yzpang.jvm.instructions.constants;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: fconst_0
 * Date: 2025/3/25 下午2:50
 **/
public class FConst0Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        frame.getOperandStack().pushFloat(0.0f);
    }
}
