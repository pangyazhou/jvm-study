package org.yzpang.jvm.runtimedata.thread;

import lombok.Data;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;

/**
 * Author: yzpang
 * Desc: 线程私有数据方法栈帧
 * Date: 2025/3/25 上午10:55
 **/
@Data
public class CustomFrame {
    /**
     * 上一个栈帧引用
     */
    private CustomFrame lower;
    /**
     * 局部变量表
     */
    private CustomLocalVariable localVariable;
    /**
     * 操作数栈
     */
    private CustomOperandStack operandStack;

    /**
     * 所属线程
     */
    private CustomThread thread;

    /**
     * 帧所属方法
     */
    private CustomMethod method;

    /**
     * 下一条指令索引
     */
    private int nextPC;

    public CustomFrame(int maxLocal, int maxStack) {
        localVariable = new CustomLocalVariable(maxLocal);
        operandStack = new CustomOperandStack(maxStack);
    }

     public CustomFrame(CustomThread thread, CustomMethod method) {
        this(method.getMaxLocal(), method.getMaxStack());
        this.thread = thread;
        this.method = method;
    }

    public void revertNextPC() {
        this.nextPC = this.thread.getPc();
    }

}
