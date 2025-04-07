package org.yzpang.jvm.instructions.stores;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.heap.CustomArrayObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: dastore 0x52
 * 将栈顶int类型数值存入指定数组的指定索引位置
 * Date: 2025/4/7 上午9:37
 **/
public class DastoreInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        double value = operandStack.popDouble();
        int index = operandStack.popInt();
        CustomArrayObject arrayObject = (CustomArrayObject) operandStack.popReference();
        checkNotNull(arrayObject);
        double[] array = arrayObject.getDoubles();
        checkIndex(array.length, index);
        array[index] = value;
    }
}
