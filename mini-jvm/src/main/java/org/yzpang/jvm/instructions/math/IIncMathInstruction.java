package org.yzpang.jvm.instructions.math;

import lombok.Data;
import lombok.Setter;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.Index8Instruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomLocalVariable;

/**
 * iinc
 * 0x84
 * 给局部变量表中的int类型参数增加常量值
 */
public class IIncMathInstruction extends Index8Instruction {
    /**
     * 增加的常量值
     */
    protected int constValue;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readByte();
        this.constValue = reader.readByte();
    }

    /**
     * 从局部变量表中指定索引取int值, 加上常量值后设置
     * @param frame
     */
    @Override
    public void execute(CustomFrame frame) {
        CustomLocalVariable localVariable = frame.getLocalVariable();
        int value = localVariable.getInt(this.index);
        value += this.constValue;
        localVariable.setInt(this.index, value);
    }
}
