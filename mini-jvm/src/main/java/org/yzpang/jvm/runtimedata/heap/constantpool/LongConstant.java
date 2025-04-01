package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class LongConstant implements CustomConstant {
    private long value;

    public LongConstant(long value) {
        this.value = value;
    }

    public long get() {
        return value;
    }
}
