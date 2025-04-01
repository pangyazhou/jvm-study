package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class FloatConstant implements CustomConstant<Float> {
    private Float value;

    public FloatConstant(Float value) {
        this.value = value;
    }

    @Override
    public Float get() {
        return value;
    }
}
