package org.yzpang.jvm.instructions.comparisons;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * dcpmg
 * 0x98
 * 比较double, 返回 -1, 0, 1
 */
public class DcmpgComparisonInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        dcmpg(frame, true);
    }
}
