package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.CustomSlots;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomField;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.heap.constantpool.FieldRefConstant;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

import java.util.Objects;

/**
 * Author: yzpang
 * Desc: putstatic 0xb3
 * 给类的静态变量赋值
 * Date: 2025/4/1 下午3:46
 **/
public class PutStaticReferenceInstruction extends Index16Instruction {

    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomMethod customMethod = frame.getMethod();
        // 当前类结构
        CustomClass currentClazz = customMethod.getClazz();
        CustomConstantPool constantPool = currentClazz.getConstantPool();
        FieldRefConstant fieldRef = (FieldRefConstant) constantPool.getConstant(this.index);
        CustomField field = fieldRef.resolvedField();
        // 字段所属类结构
        CustomClass fieldClazz = field.getClazz();
        // 实例字段报错
        if (!field.isStatic()){
            throw new IncompatibleClassChangeError();
        }
        if (field.isFinal()) {
            //  final字段必须在本类及类初始化方法中赋值
            if (currentClazz != fieldClazz || !Objects.equals(customMethod.getName(), "<clinit>")) {
                throw new IllegalAccessException();
            }
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
                slots.setInt(slotId, operandStack.popInt());
                break;
            case 'F':
                slots.setFloat(slotId, operandStack.popFloat());
                break;
            case 'J':
                slots.setLong(slotId, operandStack.popLong());
                break;
            case 'D':
                slots.setDouble(slotId, operandStack.popDouble());
                break;
            case 'L':
            case '[':
                slots.setReference(slotId, operandStack.popReference());
        }
    }
}
