package org.yzpang.jvm.instructions.base;

/**
 * Author: yzpang
 * Desc: 两字节操作数指令
 * Date: 2025/3/25 下午2:33
 **/
public class Index16Instruction extends NoOperandsInstruction{
    /**
     * 双字节操作数, 表示常量池索引
     */
    protected int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readUShort();
    }

}
