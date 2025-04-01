package org.yzpang.jvm.instructions.stack;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: dup_x1
 * 0x5a
 * Before: | ... | B | A |
 * After:  | ... | A | B | A |
 * Date: 2025/3/25 下午4:34
 **/
public class DupX1StackInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {

    }
}
