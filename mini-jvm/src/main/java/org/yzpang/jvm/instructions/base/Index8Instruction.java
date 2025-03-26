package org.yzpang.jvm.instructions.base;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 单字节操作数指令
 * Date: 2025/3/25 下午2:31
 **/
@Data
public class Index8Instruction extends NoOperandsInstruction {
    /**
     * 单字节操作数, 表示局部变量表索引
     */
    protected int index;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readByte();
    }
}
