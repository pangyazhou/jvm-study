package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.heap.CustomArrayObject;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: arraylength 0xbe
 * 获得数组的长度并压入栈顶
 * Date: 2025/4/7 上午9:24
 **/
public class ArrayLengthReferenceInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomObject array = operandStack.popReference();
        if (array == null) {
            throw new NullPointerException("获取数组长度时引用的数组对象为空");
        }
        int arrayLength = ((CustomArrayObject) array).arrayLength();
        operandStack.pushInt(arrayLength);
    }
}
