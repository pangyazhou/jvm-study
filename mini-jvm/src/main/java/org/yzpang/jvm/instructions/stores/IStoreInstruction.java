package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.Index8Instruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: istore
 * 0x36
 * Date: 2025/3/25 下午3:54
 **/
public class IStoreInstruction extends Index8Instruction {
    @Override
    public void execute(CustomFrame frame) {
        istore(frame, this.index);
    }
}
