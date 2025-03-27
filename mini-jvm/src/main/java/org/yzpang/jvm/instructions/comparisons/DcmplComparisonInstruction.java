package org.yzpang.jvm.instructions.comparisons;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * dcmpl
 * 0x97
 * 比较double, 返回 -1, 0, 1
 */
public class DcmplComparisonInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        dcmpg(frame, false);
    }
}
