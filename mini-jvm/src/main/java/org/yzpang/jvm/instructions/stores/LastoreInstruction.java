package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.heap.CustomArrayObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: lastore 0x50
 * 将栈顶long类型数值存入指定数组的指定索引位置
 * Date: 2025/4/7 上午9:37
 **/
public class LastoreInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        long value = operandStack.popLong();
        int index = operandStack.popInt();
        CustomArrayObject arrayObject = (CustomArrayObject) operandStack.popReference();
        checkNotNull(arrayObject);
        long[] array = arrayObject.getLongs();
        checkIndex(array.length, index);
        array[index] = value;
    }
}
