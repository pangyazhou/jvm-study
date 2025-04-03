package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.instructions.base.MethodInvokeLogic;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.MethodRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;
import org.yzpang.jvm.runtimedata.util.LookupUtil;

import java.util.Objects;

/**
 * Author: yzpang
 * Desc: invokevirtual 0xb6
 * 调用实例方法
 * Date: 2025/4/2 上午8:55
 **/
public class InvokeVirtualReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomClass currentClass = frame.getMethod().getClazz();
        CustomConstantPool constantPool = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index);
        CustomMethod resolvedMethod = methodRef.resolvedMethod();

        // 必须实例方法
        if (resolvedMethod.isStatic()){
            throw new IncompatibleClassChangeError();
        }
        // 获取调用方法的对象引用
        CustomObject objRef = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (objRef == null) {
            println(frame, methodRef);
            throw new NullPointerException();
        }
        // 调用的方法是protected,且该方法是当前类的父类成员,同时该方法未在同一个运行时包中声明过,
        // 那么reference所指向的对象的类型必须是当前类或当前类的子类
        if (resolvedMethod.isProtected()
                && resolvedMethod.getClazz().isSuperClassOf(currentClass)
                && !Objects.equals(resolvedMethod.getClazz().getPackageName(), currentClass.getPackageName())) {
            if (objRef.getClazz() != currentClass && !objRef.getClazz().isSubClassOf(currentClass)) {
                throw new IllegalAccessError();
            }
        }
        // 从对象引用中查找实际调用的方法
        CustomMethod methodToBeInvoked = LookupUtil.lookupMethodInClass(objRef.getClazz(), methodRef.getName(), methodRef.getDescriptor());
        if (methodToBeInvoked == null || methodToBeInvoked.isAbstract()) {
            throw new AbstractMethodError();
        }
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }

    private void println(CustomFrame frame, MethodRef methodRef){
        if (methodRef.getName().equals("println")) {
            CustomOperandStack operandStack = frame.getOperandStack();
            switch (methodRef.getDescriptor()){
                case "(Z)V":
                case "(C)V":
                case "(B)V":
                case "(S)V":
                case "(I)V":
                    System.out.printf("%s\n", operandStack.popInt());
                    break;
                case "(J)V":
                    System.out.printf("%s\n", operandStack.popLong());
                    break;
                case "(F)V":
                    System.out.printf("%s\n", operandStack.popFloat());
                    break;
                case "(D)V":
                    System.out.printf("%s\n", operandStack.popDouble());
                    break;
                default:
                    System.out.println("error: " + methodRef.getDescriptor());
            }
            operandStack.popReference();
        }
    }
}
