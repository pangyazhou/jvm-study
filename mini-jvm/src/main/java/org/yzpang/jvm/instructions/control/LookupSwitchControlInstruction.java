package org.yzpang.jvm.instructions.control;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * lookupswitch
 *          0: iload_1
 *          1: lookupswitch  { // 3
 *                     -100: 42
 *                        0: 39
 *                      100: 36
 *                  default: 44
 *             }
 *         36: bipush        24
 *         38: ireturn
 *         39: bipush        15
 *         41: ireturn
 *         42: iconst_1
 *         43: ireturn
 *         44: iconst_m1
 *         45: ireturn
 */
public class LookupSwitchControlInstruction extends BranchInstruction {
    // default 44
    private int defaultOffset;
    // case数量
    private int npairs;
    // i,i+1表示一个key-value
    private int[] matchOffsets;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        reader.skipPadding();
        defaultOffset = reader.readInt();
        npairs = reader.readInt();
        matchOffsets = reader.readInts(npairs * 2);
    }

    @Override
    public void execute(CustomFrame frame) {
        // 输入的值
        int key = frame.getOperandStack().popInt();
        int offset = defaultOffset;
        // 遍历map, 寻找匹配的case值
        for (int i = 0; i < npairs * 2; i += 2) {
            if (matchOffsets[i] == key) {
                offset = matchOffsets[i + 1];
                break;
            }
        }
        branch(frame, offset);
    }
}
