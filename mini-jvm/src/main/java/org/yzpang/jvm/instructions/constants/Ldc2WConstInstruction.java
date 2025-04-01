package org.yzpang.jvm.instructions.constants;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.constantpool.DoubleConstant;
import org.yzpang.jvm.runtimedata.heap.constantpool.LongConstant;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: ldc_w  0x14
 * 将long或double类型常量从常量池中推送至栈顶(index16)
 * Date: 2025/4/1 下午6:05
 **/
public class Ldc2WConstInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        CustomConstant constant = constantPool.getConstant(this.index);
        if (constant instanceof LongConstant) {
            LongConstant longConstant = (LongConstant) constant;
            operandStack.pushLong(longConstant.get());
        } else if (constant instanceof DoubleConstant) {
            DoubleConstant doubleConstant = (DoubleConstant) constant;
            operandStack.pushDouble(doubleConstant.get());
        } else {
            // todo
        }
    }
}
