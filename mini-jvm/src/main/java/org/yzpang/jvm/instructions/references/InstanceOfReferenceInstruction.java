package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRefConstant;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: instanceof 0xc1
 * 判断对象是否为某个类的实例
 * Date: 2025/4/1 下午5:16
 **/
public class InstanceOfReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomObject reference = operandStack.popReference();
        // null isInstanceof XXX 必为空
        if (reference == null) {
            operandStack.pushInt(0);
            return;
        }
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRefConstant classRef = (ClassRefConstant) constantPool.getConstant(this.index);
        CustomClass clazz = classRef.resolvedClass();
        if (reference.isInstanceOf(clazz)){
            operandStack.pushInt(1);
        } else {
            operandStack.pushInt(0);
        }
    }
}
