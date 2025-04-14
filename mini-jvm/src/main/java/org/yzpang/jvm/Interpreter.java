package org.yzpang.jvm;

import org.yzpang.jvm.instructions.InstructionFactory;
import org.yzpang.jvm.instructions.base.BytecodeReader;
import org.yzpang.jvm.instructions.base.CustomInstruction;
import org.yzpang.jvm.runtimedata.heap.*;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

/**
 * 解释器
 */
public class Interpreter {

    public void interpret(CustomThread thread, boolean logInst) throws Exception {
        loop(thread, logInst);
    }

    /**
     * 循环执行字节码
     * @param thread 执行线程
     * @param logInst 是否打印日志
     */
    private void loop(CustomThread thread, boolean logInst) throws Exception {
        // 当前帧
        BytecodeReader reader = new BytecodeReader();

        // 循环执行
        while (true) {
            CustomFrame frame = thread.currentFrame();
            int pc = frame.getNextPC();
            thread.setPc(pc);
            // 解码
            reader.reset(frame.getMethod().getCode(), pc);
            int opcode = reader.readUByte();
            CustomInstruction instruction = InstructionFactory.newInstruction(opcode);
            if (instruction == null) {
                System.out.println("LocalVars: " + frame.getLocalVariable());
                break;
            }
            instruction.fetchOperands(reader);
            frame.setNextPC(reader.getPc());
            if (logInst){
                logInstruction(frame, instruction);
            }
            // 执行
            instruction.execute(frame);
            // 线程执行完毕
            if (thread.isStackEmpty()){
                break;
            }
        }
    }

    private void logInstruction(CustomFrame frame, CustomInstruction instruction) throws Exception {
        CustomMethod method = frame.getMethod();
        String className = method.getClazz().getName();
        String methodName = method.getName();
        int pc = frame.getThread().getPc();
        System.out.printf("%s: %s: %2d: %s\n", className, methodName, pc, instruction);
    }
}
