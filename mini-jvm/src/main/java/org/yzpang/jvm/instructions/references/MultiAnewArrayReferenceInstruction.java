package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: multi_anew_array 0xc5
 * 创建指定类型和指定维度的多维数组(执行该指令时,操作数栈中必须包含各维度的长度值), 并将其引用压入栈顶
 * Date: 2025/4/7 上午9:56
 **/
public class MultiAnewArrayReferenceInstruction extends NoOperandsInstruction {
    // 类型在常量池中的索引
    private int index;
    // 维度
    private int dimensions;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.index = reader.readShort();
        this.dimensions = reader.readByte();
    }

    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        CustomArrayClass arrayClass = (CustomArrayClass) classRef.resolvedClass();
        CustomOperandStack operandStack = frame.getOperandStack();
        // 解析维度长度
        int[] counts = popAndCheckCounts(operandStack, this.dimensions);
        // 创建多维数组
        CustomArrayObject arrayObject = newMultiDimensionalArray(counts, arrayClass);
        operandStack.pushReference(arrayObject);
    }

    /**
     * 从操作数栈中依次弹出多维数组的各个维度长度
     * @param operandStack 操作数栈
     * @param dimensions 维度
     * @return 维度数组
     */
    private int[] popAndCheckCounts(CustomOperandStack operandStack, int dimensions) {
        int[] counts = new int[dimensions];
        for (int i = dimensions - 1; i >= 0; i--) {
            counts[i] = operandStack.popInt();
            if (counts[i] < 0) {
                throw new IndexOutOfBoundsException("创建多维数组时维度长度为负");
            }
        }
        return counts;
    }

    /**
     * 创建多维数组
     * @param counts 维度
     * @param arrayClass 数组类
     * @return 数组对象
     */
    private CustomArrayObject newMultiDimensionalArray(int[] counts, CustomArrayClass arrayClass) throws Exception {
        CustomArrayObject arrayObject = (CustomArrayObject) arrayClass.newArray(counts[0]);
        if (counts.length > 1) {
            CustomObject[] array = arrayObject.getRefs();
            for (int i = 0; i < array.length; i++) {
                array[i] = newMultiDimensionalArray(Arrays.copyOfRange(counts, 1, counts.length ), (CustomArrayClass) arrayClass.getComponentClass());
            }
        }
        return arrayObject;
    }
}
