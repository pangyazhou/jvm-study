package org.yzpang.jvm.instructions.control;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: return 0xb1
 * 从当前方法返回 void
 * Date: 2025/4/2 下午4:22
 **/
public class ReturnControlInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        frame.getThread().popFrame();
    }
}
