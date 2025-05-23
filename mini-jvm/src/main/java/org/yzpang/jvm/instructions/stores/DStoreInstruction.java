package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.Index8Instruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: dstore
 * 0x39
 * Date: 2025/3/25 下午3:54
 **/
public class DStoreInstruction extends Index8Instruction {
    @Override
    public void execute(CustomFrame frame) {
        dstore(frame, this.index);
    }
}
