package org.yzpang.jvm.runtimedata.thread;

import java.util.EmptyStackException;

/**
 * Author: yzpang
 * Desc: 线程方法栈
 * Date: 2025/3/25 上午10:53
 **/
public class CustomStack {
    /**
     * 最大深度
     */
    private int maxSize;
    /**
     * 当前深度
     */
    private int size;
    /**
     * 栈顶帧
     */
    private CustomFrame top;

    /**
     * 所属线程
     */
    private CustomThread thread;

    public CustomStack(int maxSize) {
        this.maxSize = maxSize;
    }

    /**
     * 入栈
     * @param frame
     */
    public void push(CustomFrame frame) {
        // 栈溢出
        if (size >= maxSize) {
            throw new StackOverflowError();
        }
        if (top != null) {
            frame.setLower(top);
        }
        top = frame;
        size++;
    }

    /**
     * 出栈
     * @return
     */
    public CustomFrame pop() {
        if (top == null) {
            throw new EmptyStackException();
        }
        CustomFrame frame = top;
        top = frame.getLower();
        frame.setLower(null);
        size--;
        return frame;
    }

    /**
     * 栈顶元素
     * @return
     */
    public CustomFrame top() {
        if (top == null) {
            throw new EmptyStackException();
        }
        return top;
    }
}
