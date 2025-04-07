package org.yzpang.jvm.instructions.loads;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.heap.CustomArrayObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: baload 0x33
 * 将byte/boolean类型数组的指定元素推送至栈顶
 * Date: 2025/4/7 上午9:28
 **/
public class BaloadInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        int index = operandStack.popInt();
        CustomArrayObject arrayObject = (CustomArrayObject) operandStack.popReference();
        checkNotNull(arrayObject);
        byte[] array = arrayObject.getBytes();
        checkIndex(array.length, index);
        operandStack.pushInt(array[index]);
    }
}
