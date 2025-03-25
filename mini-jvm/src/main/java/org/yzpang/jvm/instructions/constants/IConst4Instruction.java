package org.yzpang.jvm.instructions.constants;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: iconst_4
 * Date: 2025/3/25 下午2:50
 **/
public class IConst4Instruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        frame.getOperandStack().pushInt(4);
    }
}
