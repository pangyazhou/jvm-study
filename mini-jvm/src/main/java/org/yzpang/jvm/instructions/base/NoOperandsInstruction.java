package org.yzpang.jvm.instructions.base;

import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

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
    public void execute(CustomFrame frame) throws Exception {
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
        CustomObject value = frame.getLocalVariable().getReference(index);
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
        CustomObject value = frame.getOperandStack().popReference();
        frame.getLocalVariable().setReference(index, value);
    }

    /**
     * 比较float
     */
    protected final void fcmpg(CustomFrame frame, boolean gFlag) {
        CustomOperandStack operandStack = frame.getOperandStack();
        float var1 = operandStack.popFloat();
        float var2 = operandStack.popFloat();
        if (var2 > var1){
            operandStack.pushInt(1);
        } else if (var2 == var1){
            operandStack.pushInt(0);
        } else if (var2 < var1) {
            operandStack.pushInt(-1);
        } else if (gFlag) {
            operandStack.pushInt(1);
        } else {
            operandStack.pushInt(-1);
        }
    }

    /**
     * 比较double
     */
    protected final void dcmpg(CustomFrame frame, boolean gFlag) {
        CustomOperandStack operandStack = frame.getOperandStack();
        double var1 = operandStack.popDouble();
        double var2 = operandStack.popDouble();
        if (var2 > var1){
            operandStack.pushInt(1);
        } else if (var2 == var1){
            operandStack.pushInt(0);
        } else if (var2 < var1) {
            operandStack.pushInt(-1);
        } else if (gFlag) {
            operandStack.pushInt(1);
        } else {
            operandStack.pushInt(-1);
        }
    }


}
