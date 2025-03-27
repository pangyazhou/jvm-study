package org.yzpang.jvm.instructions.control;

import org.yzpang.jvm.instructions.base.BranchInstruction;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * tableswitch
 * 0xaa
 *          0: iload_1
 *          1: tableswitch   { // 1 to 6
 *                        1: 40
 *                        2: 42
 *                        3: 45
 *                        4: 51
 *                        5: 51
 *                        6: 48
 *                  default: 51
 *             }
 *         40: iconst_5
 *         41: ireturn
 *         42: bipush        10
 *         44: ireturn
 *         45: bipush        20
 *         47: ireturn
 *         48: bipush        30
 *         50: ireturn
 *         51: iconst_0
 *         52: ireturn
 */
public class TableSwitchControlInstruction extends BranchInstruction {
    // default 偏移量 51
    private int defaultOffset;
    // 1 to 6
    private int low;
    private int high;
    // case 偏移量数组 [high - low + 1]
    private int[] jumpOffset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        // 4字节对齐
        reader.skipPadding();
        this.defaultOffset = reader.readInt();
        this.low = reader.readInt();
        this.high = reader.readInt();
        int count = high - low + 1;
        this.jumpOffset = reader.readInts(count);
    }

    @Override
    public void execute(CustomFrame frame) {
        int index = frame.getOperandStack().popInt();
        int offset = defaultOffset;
        if (index >= this.low && index <= this.high) {
            offset = this.jumpOffset[index - this.low];
        }
        branch(frame, offset);
    }
}
