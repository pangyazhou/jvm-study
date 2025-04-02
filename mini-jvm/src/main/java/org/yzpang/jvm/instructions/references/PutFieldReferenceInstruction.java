package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.heap.constantpool.FieldRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

import java.util.Objects;

/**
 * Author: yzpang
 * Desc: putfield 0xb5
 * 给实例变量赋值
 * Date: 2025/4/1 下午4:35
 **/
public class PutFieldReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomMethod currentMethod = frame.getMethod();
        CustomClass currentClazz = currentMethod.getClazz();
        CustomConstantPool constantPool = currentClazz.getConstantPool();
        FieldRef fieldRef = (FieldRef) constantPool.getConstant(this.index);
        CustomField field = fieldRef.resolvedField();
        // 必须是实例变量
        if (field.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        // final 必须在本类的构造函数中初始化
        if (field.isFinal()) {
            if (currentClazz != field.getClazz() || !Objects.equals(currentMethod.getName(), "<init>")){
                throw new IllegalAccessException();
            }
        }
        String descriptor = field.getDescriptor();
        int slotId = field.getSlotId();
        CustomOperandStack operandStack = frame.getOperandStack();
        switch (descriptor.charAt(0)) {
            case 'Z':
            case 'B':
            case 'C':
            case 'S':
            case 'I':
                int intValue = operandStack.popInt();
                CustomObject intRef = operandStack.popReference();
                if (intRef == null){
                    throw new NullPointerException();
                }
                intRef.getFields().setInt(slotId, intValue);
                break;
            case 'F':
                float floatValue = operandStack.popFloat();
                CustomObject floatRef = operandStack.popReference();
                if (floatRef == null){
                    throw new NullPointerException();
                }
                floatRef.getFields().setFloat(slotId, floatValue);
                break;
            case 'J':
                long longValue = operandStack.popLong();
                CustomObject longRef = operandStack.popReference();
                if (longRef == null){
                    throw new NullPointerException();
                }
                longRef.getFields().setLong(slotId, longValue);
                break;
            case 'D':
                double doubleValue = operandStack.popDouble();
                CustomObject doubleRef = operandStack.popReference();
                if (doubleRef == null){
                    throw new NullPointerException();
                }
                doubleRef.getFields().setDouble(slotId, doubleValue);
                break;
            case 'L':
            case '[':
                CustomObject referenceValue = operandStack.popReference();
                CustomObject referenceRef = operandStack.popReference();
                if (referenceRef == null){
                    throw new NullPointerException();
                }
                referenceRef.getFields().setReference(slotId, referenceValue);
        }
    }
}
