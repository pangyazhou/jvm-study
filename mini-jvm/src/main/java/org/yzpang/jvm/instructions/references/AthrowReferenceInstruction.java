package org.yzpang.jvm.instructions.references;

import org.yzpang.jvm.instructions.base.NoOperandsInstruction;
import org.yzpang.jvm.runtimedata.heap.CustomObject;
import org.yzpang.jvm.runtimedata.heap.CustomStackTraceElement;
import org.yzpang.jvm.runtimedata.heap.CustomStringPool;
import org.yzpang.jvm.runtimedata.thread.CustomFrame;
import org.yzpang.jvm.runtimedata.thread.CustomOperandStack;
import org.yzpang.jvm.runtimedata.thread.CustomThread;

/**
 * Author: yzpang
 * Desc: athrow 0xbf
 * 将栈顶的异常抛出
 * Date: 2025/4/11 上午10:13
 **/
public class AthrowReferenceInstruction extends NoOperandsInstruction {
    @Override
    public void execute(CustomFrame frame) throws Exception {
        CustomObject exObj = frame.getOperandStack().popReference();
        if (exObj == null) {
            throw new NullPointerException("异常抛出对象为空");
        }
        CustomThread thread = frame.getThread();
        if (!findAndGotoExceptionHandler(thread, exObj)) {
            handleUncaughtException(thread, exObj);
        }
    }

    /**
     * 查找异常处理器并处理异常
     * @param thread 发生异常的线程
     * @param exObj 异常对象
     * @return 处理成功与否
     */
    private boolean findAndGotoExceptionHandler(CustomThread thread, CustomObject exObj) throws Exception {
        while (true) {
            CustomFrame frame = thread.currentFrame();
            int pc = frame.getNextPC() - 1;
            int handlerPc = frame.getMethod().findExceptionHandler(exObj.getClazz(), pc);
            if (handlerPc > 0) {
                CustomOperandStack operandStack = frame.getOperandStack();
                operandStack.clear();
                operandStack.pushReference(exObj);
                frame.setNextPC(handlerPc);
                return true;
            }
            thread.popFrame();
            if (thread.isStackEmpty()){
                break;
            }
        }
        return false;
    }

    /**
     * 递归查找未发现异常处理器
     * @param thread 处理线程
     * @param exObj 异常对象
     */
    private void handleUncaughtException(CustomThread thread, CustomObject exObj) {
        thread.clearStack();
        CustomObject message = exObj.getRefVar("detailMessage", "Ljava/lang/String;");
        String strMsg = CustomStringPool.goString(message);
        System.out.println(exObj.getClazz().getJavaName() + ": " + strMsg);
        // 打印虚拟机栈信息
        CustomStackTraceElement[] stackTrace = exObj.getStackTrace();
        for (int i = 0; i < stackTrace.length; i++) {
            CustomStackTraceElement element = stackTrace[i];
            System.out.println("\tat " + element.toString());
        }
    }
}
