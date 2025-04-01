package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class DoubleConstant implements CustomConstant {
    private double value;

    public DoubleConstant(double value) {
        this.value = value;
    }

    public double get() {
        return value;
    }
}
