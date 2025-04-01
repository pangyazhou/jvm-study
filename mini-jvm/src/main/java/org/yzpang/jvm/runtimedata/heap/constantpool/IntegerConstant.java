package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class IntegerConstant implements CustomConstant {
    private int value;

    public IntegerConstant(Integer value) {
        this.value = value;
    }

    public int get() {
        return value;
    }
}
