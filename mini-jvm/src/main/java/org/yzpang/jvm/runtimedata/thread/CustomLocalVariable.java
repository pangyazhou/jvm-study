package org.yzpang.jvm.runtimedata.thread;

import org.yzpang.jvm.runtimedata.CustomSlots;

/**
 * Author: yzpang
 * Desc: 本地变量表
 * Date: 2025/3/25 上午11:11
 **/
public class CustomLocalVariable extends CustomSlots {
    private int maxLocals;

    public CustomLocalVariable(int maxLocals) {
        super(maxLocals);
        this.maxLocals = maxLocals;
    }

}
