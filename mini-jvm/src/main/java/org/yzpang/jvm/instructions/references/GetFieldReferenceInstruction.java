package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.CustomSlots;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomField;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.FieldRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: getfield 0xb4
 * 读取实例变量的值
 * Date: 2025/4/1 下午4:58
 **/
public class GetFieldReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) constantPool.getConstant(this.index);
        CustomField field = fieldRef.resolvedField();
        // 必须为实例变量
        if (field.isStatic()){
            throw new IncompatibleClassChangeError();
        }
        CustomOperandStack operandStack = frame.getOperandStack();
        // 引用变量的对象
        CustomObject reference = operandStack.popReference();
        if (reference == null) {
            throw new NullPointerException();
        }
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        CustomSlots slots = reference.getFields();
        switch (descriptor.charAt(0)){
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                operandStack.pushInt(slots.getInt(slotId));
                break;
            case 'F':
                operandStack.pushFloat(slots.getFloat(slotId));
                break;
            case 'J':
                operandStack.pushLong(slots.getLong(slotId));
                break;
            case 'D':
                operandStack.pushDouble(slots.getDouble(slotId));
                break;
            case 'L':
            case '[':
                operandStack.pushReference(slots.getReference(slotId));
        }
    }
}
