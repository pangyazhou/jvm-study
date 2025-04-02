package org.yzpang.jvm.instructions.constants;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRef;
import org.yzpang.jvm.runtimedata.heap.constantpool.FloatConstant;
import org.yzpang.jvm.runtimedata.heap.constantpool.IntegerConstant;
import org.yzpang.jvm.runtimedata.heap.constantpool.StringConstant;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: ldc_w  0x13
 * 将int, float或String类型常量从常量池中推送至栈顶(index16)
 * Date: 2025/4/1 下午6:05
 **/
public class LdcWConstInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        CustomConstant constant = constantPool.getConstant(this.index);
        if (constant instanceof IntegerConstant) {
            IntegerConstant integerConstant = (IntegerConstant) constant;
            operandStack.pushInt(integerConstant.get());
        } else if (constant instanceof FloatConstant) {
            FloatConstant floatConstant = (FloatConstant) constant;
            operandStack.pushFloat(floatConstant.get());
        } else if (constant instanceof StringConstant) {
            StringConstant stringConstant = (StringConstant) constant;
            // todo
        } else if (constant instanceof ClassRef) {
            ClassRef classRef = (ClassRef) constant;
            // todo
        }
    }
}
