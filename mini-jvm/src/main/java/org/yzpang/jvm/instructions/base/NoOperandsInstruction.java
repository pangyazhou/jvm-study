package org.yzpang.jvm.instructions.base;

import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: 无操作数指令
 * Date: 2025/3/25 下午2:10
 **/
public class NoOperandsInstruction implements CustomInstruction{
    @Override
    public void fetchOperands(BytecodeReader reader) {
        // nothing to do
    }

    @Override
    public void execute(CustomFrame frame) {
    }

    protected final void iload(CustomFrame frame, int index) {
        int value = frame.getLocalVariable().getInt(index);
        frame.getOperandStack().pushInt(value);
    }
    protected final void fload(CustomFrame frame, int index) {
        float value = frame.getLocalVariable().getFloat(index);
        frame.getOperandStack().pushFloat(value);
    }
    protected final void lload(CustomFrame frame, int index) {
        long value = frame.getLocalVariable().getLong(index);
        frame.getOperandStack().pushLong(value);
    }
    protected final void dload(CustomFrame frame, int index) {
        double value = frame.getLocalVariable().getDouble(index);
        frame.getOperandStack().pushDouble(value);
    }
    protected final void aload(CustomFrame frame, int index) {
        Object value = frame.getLocalVariable().getReference(index);
        frame.getOperandStack().pushReference(value);
    }
    protected final void istore(CustomFrame frame, int index) {
        int value = frame.getOperandStack().popInt();
        frame.getLocalVariable().setInt(index, value);
    }
    protected final void lstore(CustomFrame frame, int index) {
        long value = frame.getOperandStack().popLong();
        frame.getLocalVariable().setLong(index, value);
    }
    protected final void fstore(CustomFrame frame, int index) {
        float value = frame.getOperandStack().popFloat();
        frame.getLocalVariable().setFloat(index, value);
    }
    protected final void dstore(CustomFrame frame, int index) {
        double value = frame.getOperandStack().popDouble();
        frame.getLocalVariable().setDouble(index, value);
    }
    protected final void astore(CustomFrame frame, int index) {
        Object value = frame.getOperandStack().popReference();
        frame.getLocalVariable().setReference(index, value);
    }

}
