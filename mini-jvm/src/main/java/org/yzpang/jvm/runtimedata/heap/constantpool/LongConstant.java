package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class LongConstant implements CustomConstant<Long> {
    private long value;

    public LongConstant(long value) {
        this.value = value;
    }

}
