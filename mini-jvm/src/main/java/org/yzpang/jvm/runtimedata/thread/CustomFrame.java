package org.yzpang.jvm.runtimedata.thread;

import lombok.Data;

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
     * 所属栈
     */
    private CustomStack stack;
    /**
     * 所属线程
     */
    private CustomThread thread;

    public CustomFrame(int maxLocal, int maxStack) {
        localVariable = new CustomLocalVariable(maxLocal);
        operandStack = new CustomOperandStack(maxStack);
    }
}
