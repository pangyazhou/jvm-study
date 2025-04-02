package org.yzpang.jvm;

import org.yzpang.jvm.instructions.InstructionFactory;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.CustomInstruction;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

/**
 * 解释器
 */
public class Interpreter {

    /**
     * 解释执行方法
     * @param method 方法信息
     */
    public void interpret(CustomMethod method) throws Exception {
        CustomThread thread = new CustomThread();
        CustomFrame frame = thread.newFrame(method);
        thread.pushFrame(frame);
        loop(thread, method.getCode());
    }

    /**
     * 循环执行字节码
     * @param thread
     * @param bytecode
     */
    private void loop(CustomThread thread, byte[] bytecode) throws Exception {
        // 当前帧
        CustomFrame frame = thread.popFrame();
        BytecodeReader reader = new BytecodeReader(bytecode);

        while (true) {
            int pc = frame.getNextPC();
            thread.setPc(pc);
            // 解码
            reader.reset(bytecode, pc);
            int opcode = reader.readUByte();
            CustomInstruction instruction = InstructionFactory.newInstruction(opcode);
            if (instruction == null) {
                System.out.println("LocalVars: " + frame.getLocalVariable());
                break;
            }
            instruction.fetchOperands(reader);
            frame.setNextPC(reader.getPc());
            // 执行
            instruction.execute(frame);
        }
    }
}
