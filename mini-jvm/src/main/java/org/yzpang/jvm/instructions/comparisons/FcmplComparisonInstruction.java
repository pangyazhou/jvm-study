package org.yzpang.jvm.instructions.comparisons;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * fcpml
 * 比较float, 返回 -1, 0, 1
 */
public class FcmplComparisonInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) {
        fcmpg(frame, false);
    }
}
