package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.ClassInitLogic;
import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.instructions.base.MethodInvokeLogic;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.heap.constantpool.MethodRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: invokestatic 0xb8
 * 调用静态方法
 * Date: 2025/4/2 下午4:36
 **/
public class InvokeStaticReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        MethodRef methodRef = (MethodRef) constantPool.getConstant(this.index);
        CustomMethod resolvedMethod = methodRef.resolvedMethod();
        CustomClass methodClass = resolvedMethod.getClazz();
        // 判断类有没有初始化
        if (!methodClass.initStarted()){
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), methodClass);
            return;
        }
        // 必须是静态方法
        if (!resolvedMethod.isStatic()) {
            throw new IncompatibleClassChangeError();
        }
        MethodInvokeLogic.invokeMethod(frame, resolvedMethod);
    }
}
