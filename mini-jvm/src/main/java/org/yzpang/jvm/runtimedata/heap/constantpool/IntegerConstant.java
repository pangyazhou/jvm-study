package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class IntegerConstant implements CustomConstant<Integer> {
    private Integer value;

    public IntegerConstant(Integer value) {
        this.value = value;
    }

    @Override
    public Integer get() {
        return value;
    }
}
