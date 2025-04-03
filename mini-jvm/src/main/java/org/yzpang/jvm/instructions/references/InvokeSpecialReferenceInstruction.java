package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.instructions.base.MethodInvokeLogic;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.MethodRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.util.LookupUtil;

import java.util.Objects;

/**
 * Author: yzpang
 * Desc: invoke_special 0xb7
 * 调用父类方法/实例初始化方法/私有方法
 * Date: 2025/4/2 上午8:50
 **/
public class InvokeSpecialReferenceInstruction extends Index16Instruction {

    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomClass currentClass = frame.getMethod().getClazz();
        CustomConstantPool constantPool = currentClass.getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index);
        CustomClass resolvedClass = methodRef.resolvedClass();
        CustomMethod resolvedMethod = methodRef.resolvedMethod();

        // init方法必须解析类与声明类相同(不能使用父类的实例初始化方法)
        if (resolvedMethod.getName().equals("<init>")
            && resolvedMethod.getClazz() != resolvedClass) {
            throw new NoSuchMethodError();
        }
        // 必须是实例方法
        if (resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        // 获取方法调用对象的引用
        CustomObject objRef = frame.getOperandStack().getRefFromTop(resolvedMethod.getArgSlotCount() - 1);
        if (objRef == null) {
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
        // 调用的是超类中的方法,且不是构造方法,当前类的acc_super为True, 从当前类的超类中查找方法
        CustomMethod methodToBeInvoked = resolvedMethod;
        if (currentClass.isSuper()
                && resolvedClass.isSuperClassOf(currentClass)
                && !Objects.equals(resolvedMethod.getName(), "<init>")) {
            methodToBeInvoked = LookupUtil.lookupMethodInClass(currentClass.getSuperClass(), methodRef.getName(), methodRef.getDescriptor());
        }
        if (methodToBeInvoked == null
            || methodToBeInvoked.isAbstract()){
            throw new AbstractMethodError();
        }
        MethodInvokeLogic.invokeMethod(frame, methodToBeInvoked);
    }
}
