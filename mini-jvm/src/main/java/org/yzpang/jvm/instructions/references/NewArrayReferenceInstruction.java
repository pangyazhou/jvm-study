package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.constant.ArrayConstants;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.heap.CustomArrayClass;
import org.yzpang.jvm.runtimedata.heap.CustomClassLoader;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;

/**
 * Author: yzpang
 * Desc: newarray 0xbc
 * 创建一个原始类型的数组,并将其引用值压入栈顶
 * Date: 2025/4/7 上午8:47
 **/
public class NewArrayReferenceInstruction extends NoOperandsInstruction {
    private byte arrayType;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        this.arrayType = reader.readByte();
    }

    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomOperandStack operandStack = frame.getOperandStack();
        int count = operandStack.popInt();
        if (count < 0) {
            throw new NegativeArraySizeException("创建数组时传入大小为负数");
        }
        CustomClassLoader classloader = frame.getMethod().getClazz().getClassloader();
        CustomArrayClass arrayClass = getPrimitiveArrayClass(classloader, this.arrayType);
        CustomObject array = arrayClass.newArray(count);
        operandStack.pushReference(array);
    }

    /**
     * 构建基本类型数组类结构
     * @param classLoader 类加载器
     * @param arrayType 数组类型
     * @return arrayClass
     */
    private CustomArrayClass getPrimitiveArrayClass(CustomClassLoader classLoader, byte arrayType) throws Exception {
        switch (arrayType) {
            case ArrayConstants.AT_BOOLEAN:
                return (CustomArrayClass) classLoader.loadClass("[Z");
            case ArrayConstants.AT_BYTE:
                return (CustomArrayClass) classLoader.loadClass("[B");
            case ArrayConstants.AT_CHAR:
                return (CustomArrayClass) classLoader.loadClass("[C");
            case ArrayConstants.AT_DOUBLE:
                return (CustomArrayClass) classLoader.loadClass("[D");
            case ArrayConstants.AT_FLOAT:
                return (CustomArrayClass) classLoader.loadClass("[F");
            case ArrayConstants.AT_INT:
                return (CustomArrayClass) classLoader.loadClass("[I");
            case ArrayConstants.AT_LONG:
                return (CustomArrayClass) classLoader.loadClass("[J");
            case ArrayConstants.AT_SHORT:
                return (CustomArrayClass) classLoader.loadClass("[S");
            default:
                throw new Exception("非法的数组类型: " + arrayType);
        }
    }
}
