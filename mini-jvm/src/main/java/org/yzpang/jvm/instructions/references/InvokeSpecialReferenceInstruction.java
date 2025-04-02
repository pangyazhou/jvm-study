package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: invokespecial 0xb7
 * 调用父类方法/实例初始化方法/私有方法
 * Date: 2025/4/2 上午8:50
 **/
public class InvokeSpecialReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        frame.getOperandStack().popReference();
    }
}
