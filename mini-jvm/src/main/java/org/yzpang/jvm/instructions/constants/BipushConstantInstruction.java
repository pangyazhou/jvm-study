package org.yzpang.jvm.instructions.constants;

import org.yzpang.jvm.instructions.base.Index8Instruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: bipush
 * 0x10
 * Date: 2025/3/25 下午2:58
 **/
public class BipushConstantInstruction extends Index8Instruction {
    @Override
    public void execute(CustomFrame frame) {
        frame.getOperandStack().pushInt(this.index);
    }
}
