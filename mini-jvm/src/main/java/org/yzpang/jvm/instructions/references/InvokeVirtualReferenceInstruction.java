package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.constantpool.MethodRefConstant;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: invokevirtual 0xb6
 * 调用实例方法
 * Date: 2025/4/2 上午8:55
 **/
public class InvokeVirtualReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        MethodRefConstant methodRef = (MethodRefConstant) constantPool.getConstant(this.index);
        if (methodRef.getName().equals("println")) {
            CustomOperandStack operandStack = frame.getOperandStack();
            switch (methodRef.getDescriptor()){
                case "(Z)V":
                case "(C)V":
                case "(B)V":
                case "(S)V":
                case "(I)V":
                    System.out.printf("%s\n", operandStack.popInt());
                    break;
                case "(J)V":
                    System.out.printf("%s\n", operandStack.popLong());
                    break;
                case "(F)V":
                    System.out.printf("%s\n", operandStack.popFloat());
                    break;
                case "(D)V":
                    System.out.printf("%s\n", operandStack.popDouble());
                    break;
                default:
                    throw new Exception(methodRef.getDescriptor());
            }
            operandStack.popReference();
        }
    }
}
