package org.yzpang.jvm.instructions.constants;

import org.yzpang.jvm.instructions.base.Index8Instruction;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.heap.constantpool.*;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: ldc  0x12
 * 将int, float或String类型常量从常量池中推送至栈顶(index8)
 * Date: 2025/4/1 下午6:05
 **/
public class LdcConstInstruction extends Index8Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        CustomClass clazz = frame.getMethod().getClazz();
        CustomConstantPool constantPool = clazz.getConstantPool();
        CustomConstant constant = constantPool.getConstant(this.index);
        if (constant instanceof IntegerConstant) {
            IntegerConstant integerConstant = (IntegerConstant) constant;
            operandStack.pushInt(integerConstant.get());
        } else if (constant instanceof FloatConstant) {
            FloatConstant floatConstant = (FloatConstant) constant;
            operandStack.pushFloat(floatConstant.get());
        } else if (constant instanceof StringConstant) {
            StringConstant stringConstant = (StringConstant) constant;
            CustomObject internedStr = CustomStringPool.jString(clazz.getClassloader(), stringConstant.getValue());
            operandStack.pushReference(internedStr);
        } else if (constant instanceof ClassRef) {
            ClassRef classRef = (ClassRef) constant;
            CustomObject classObject = classRef.resolvedClass().getJClass();
            operandStack.pushReference(classObject);
        }
    }
}
