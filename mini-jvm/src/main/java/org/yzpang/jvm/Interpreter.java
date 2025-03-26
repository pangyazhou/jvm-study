package org.yzpang.jvm;

import org.yzpang.jvm.classfile.MethodInfo;
import org.yzpang.jvm.classfile.attribute.CodeAttribute;
import org.yzpang.jvm.instructions.InstructionFactory;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.CustomInstruction;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

/**
 * 解释器
 */
public class Interpreter {

    /**
     * 解释执行方法
     * @param methodInfo 方法信息
     */
    public void interpret(MethodInfo methodInfo) {
        CodeAttribute codeAttribute = methodInfo.getCodeAttribute();
        // 操作数栈
        int maxStack = codeAttribute.getMaxStack();
        // 局部变量表
        int maxLocals = codeAttribute.getMaxLocals();
        // 字节码数组
        byte[] bytecode = codeAttribute.getCode();

        CustomThread thread = new CustomThread();
        CustomFrame frame = thread.newFrame(maxLocals, maxStack);
        thread.pushFrame(frame);

    }

    /**
     * 循环执行字节码
     * @param thread
     * @param bytecode
     */
    private void loop(CustomThread thread, byte[] bytecode) {
        // 当前帧
        CustomFrame frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader(bytecode);

        int pc = frame.getNextPC();
        thread.setPc(pc);

        // 解码
        reader.reset(bytecode, pc);
        int opcode = reader.readUByte();
        CustomInstruction instruction = InstructionFactory.newInstruction(opcode);
        instruction.fetchOperands(reader);
        frame.setNextPC(reader.getPc());
        // 执行
        instruction.execute(frame);
    }
}
