package org.yzpang.jvm.instructions.base;

/**
 * Author: yzpang
 * Desc: 单字节操作数指令
 * Date: 2025/3/25 下午2:31
 **/
public class Index8Instruction extends NoOperandsInstruction {
    /**
     * 单字节操作数, 表示局部变量表索引
     */
    protected byte index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readByte();
    }
}
