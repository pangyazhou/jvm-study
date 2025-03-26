package org.yzpang.jvm.instructions.extended;

import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.CustomInstruction;
import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.instructions.loads.ILoadInstruction;
import org.yzpang.jvm.instructions.math.IIncMathInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * wide
 * 扩展指令
 */
public class WideExtendedInstruction extends NoOperandsInstruction {
    /**
     * 扩展后的指令
     */
    private CustomInstruction modifiedInstruction;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        int opcode = reader.readUByte();
        switch (opcode) {
            case 0x15:
                // iload
                ILoadInstruction iLoadInstruction = new ILoadInstruction();
                iLoadInstruction.setIndex(reader.readShort());
                this.modifiedInstruction = iLoadInstruction;
                break;
            case 0x16:
                break;
            case 0x84:
                // iinc
                IIncMathInstruction iIncMathInstruction = new IIncMathInstruction();
                iIncMathInstruction.setIndex(reader.readShort());
                iIncMathInstruction.setConstValue(reader.readShort());
                this.modifiedInstruction = iIncMathInstruction;
                break;
            default:
                break;
        }
    }

    @Override
    public void execute(CustomFrame frame) {
        this.modifiedInstruction.execute(frame);
    }
}
