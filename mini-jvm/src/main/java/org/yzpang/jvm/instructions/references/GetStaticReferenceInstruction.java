package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.ClassInitLogic;
import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.CustomSlots;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomField;
import org.yzpang.jvm.runtimedata.heap.constantpool.FieldRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: getstatic 0xb2
 * 读取静态变量
 * Date: 2025/4/1 下午4:23
 **/
public class GetStaticReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        FieldRef fieldRef = (FieldRef) constantPool.getConstant(this.index);
        CustomField field = fieldRef.resolvedField();
        CustomClass fieldClazz = field.getClazz();
        // 判断类有没有初始化
        if (!fieldClazz.initStarted()){
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), fieldClazz);
            return;
        }
        // 必须是静态变量
        if (!field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        CustomSlots slots = fieldClazz.getStaticVariables();
        CustomOperandStack operandStack = frame.getOperandStack();
        switch (descriptor.charAt(0)) {
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
