package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class FloatConstant implements CustomConstant {
    private float value;

    public FloatConstant(Float value) {
        this.value = value;
    }

    public float get() {
        return value;
    }
}
