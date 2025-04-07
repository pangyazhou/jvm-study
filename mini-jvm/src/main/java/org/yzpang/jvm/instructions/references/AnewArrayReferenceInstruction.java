package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.CustomArrayClass;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: anewarray 0xbd
 * 创建一个引用类型(类/接口/数组)的数组,并将其引用压入栈顶
 * Date: 2025/4/7 上午9:06
 **/
public class AnewArrayReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        CustomClass resolvedClass = classRef.resolvedClass();
        CustomOperandStack operandStack = frame.getOperandStack();
        int count = operandStack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("创建数组时大小为负数");
        }
        CustomArrayClass arrayClass = resolvedClass.getArrayClass();
        CustomObject array = arrayClass.newArray(count);
        operandStack.pushReference(array);
    }
}
