package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: checkcast 0xc0
 * 检验类型转换, 检验失败抛出ClassCastException
 * Date: 2025/4/1 下午5:20
 **/
public class CheckCastReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomObject reference = operandStack.popReference();
        operandStack.pushReference(reference);
        if (reference == null) {
            return;
        }
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        CustomClass clazz = classRef.resolvedClass();
        if (!reference.isInstanceOf(clazz)) {
            throw new ClassCastException();
        }
    }
}
