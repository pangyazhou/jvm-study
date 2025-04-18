package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.ClassInitLogic;
import org.yzpang.jvm.instructions.base.Index16Instruction;
import org.yzpang.jvm.runtimedata.heap.CustomClass;
import org.yzpang.jvm.runtimedata.heap.CustomConstantPool;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.constantpool.ClassRef;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;

/**
 * Author: yzpang
 * Desc: new 0xbb
 * 创建一个对象,并将其引用值压入栈顶
 * Date: 2025/4/1 下午3:06
 **/
public class NewReferenceInstruction extends Index16Instruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomConstantPool constantPool = frame.getMethod().getClazz().getConstantPool();
        ClassRef classRef = (ClassRef) constantPool.getConstant(this.index);
        CustomClass clazz = classRef.resolvedClass();
        // 判断类有没有初始化
        if (!clazz.initStarted()){
            frame.revertNextPC();
            ClassInitLogic.initClass(frame.getThread(), clazz);
            return;
        }
        // 不能是接口与抽象类
        if (clazz.isInterface() || clazz.isAbstract()) {
            throw new InstantiationError();
        }
        CustomObject object = clazz.newObject();
        frame.getOperandStack().pushReference(object);
    }
}
