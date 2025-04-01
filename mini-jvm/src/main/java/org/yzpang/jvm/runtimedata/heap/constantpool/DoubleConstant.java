package org.yzpang.jvm.runtimedata.heap.constantpool;

import org.yzpang.jvm.runtimedata.heap.CustomConstant;

public class DoubleConstant implements CustomConstant<Double> {
    private double value;

    public DoubleConstant(double value) {
        this.value = value;
    }

    @Override
    public Double get() {
        return value;
    }
}
