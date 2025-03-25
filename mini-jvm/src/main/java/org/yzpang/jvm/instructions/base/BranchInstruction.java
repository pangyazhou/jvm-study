package org.yzpang.jvm.instructions.base;

/**
 * Author: yzpang
 * Desc: 跳转指令
 * Date: 2025/3/25 下午2:29
 **/
public class BranchInstruction extends NoOperandsInstruction {
    // 跳转偏移量
    protected short offset;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        offset = reader.readShort();
    }

}
