package org.yzpang.jvm.runtimedata.thread;

import lombok.Data;
import org.yzpang.jvm.runtimedata.heap.CustomMethod;

/**
 * Author: yzpang
 * Desc: 线程私有数据
 * Date: 2025/3/25 上午10:53
 **/
@Data
public class CustomThread {
    /**
     * PC寄存器
     */
    private int pc;
    /**
     * 方法栈
     */
    private CustomStack customStack;

    public CustomThread() {
        this.customStack = new CustomStack(1024);
    }

    /**
     * 栈帧入栈
     */
    public void pushFrame(CustomFrame frame){
        customStack.push(frame);
    }

    /**
     * 栈帧出栈
     */
    public CustomFrame popFrame(){
        return customStack.pop();
    }

    /**
     * 当前帧
     */
    public CustomFrame currentFrame(){
        return customStack.top();
    }

    /**
     * 新建帧
     * @param method 方法对象
     * @return frame
     */
    public CustomFrame newFrame(CustomMethod method){
        return new CustomFrame(this, method);
    }
}
