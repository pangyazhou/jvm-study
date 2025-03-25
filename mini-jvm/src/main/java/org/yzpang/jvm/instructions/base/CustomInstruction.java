package org.yzpang.jvm.instructions.base;

import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * 指令接口
 */
public interface CustomInstruction {
    void fetchOperands(BytecodeReader reader);
    void execute(CustomFrame frame);
}
