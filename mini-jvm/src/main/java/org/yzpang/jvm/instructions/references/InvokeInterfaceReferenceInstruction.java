package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.instructions.base.MethodInvokeLogic;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.InterfaceMethodRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.util.LookupUtil;

/**
 * Author: yzpang
 * Desc: invoke_interface 0xb9
 * 调用接口方法
 * Date: 2025/4/3 上午11:04
 **/
public class InvokeInterfaceReferenceInstruction extends Index16Instruction {
    // 参数所属slot数量
    private int count;
    private int zero;

    @Override
    public void fetchOperands(BytecodeReader reader) {
        index = reader.readShort();
        reader.readUByte();
        reader.readUByte();
    }

    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        InterfaceMethodRef methodRef = (InterfaceMethodRef) constantPool.getConstant(this.index);
        CustomClass resolvedClass = methodRef.resolvedClass();
        CustomMethod resolvedMethod = methodRef.resolvedInterfaceMethod();

        // 接口方法不能是static和private
        if (resolvedMethod.isStatic() || resolvedMethod.isPrivate()) {
            throw new IncompatibleClassChangeError();
        }

        // 方法调用对象的引用
        CustomObject objRef = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (objRef == null) {
            throw new NullPointerException();
        }
        // 方法调用对象类必须是方法声明接口的实现
        if (!objRef.getClazz().isImplements(resolvedClass)) {
            throw new IncompatibleClassChangeError();
        }

        // 从方法调用对象所在类开始查找真正调用的方法对象
        CustomMethod methodToBeInvoked = LookupUtil.lookupMethodInClass(objRef.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new NoSuchMethodException();
        }
        // 接口方法实现必须是public
        if (!methodToBeInvoked.isPublic()) {
            throw new IllegalAccessException();
        }
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
