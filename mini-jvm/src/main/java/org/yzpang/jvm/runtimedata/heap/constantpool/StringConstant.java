package org.yzpang.jvm.runtimedata.heap.constantpool;

import lombok.Data;
import org.yzpang.jvm.runtimedata.heap.CustomConstant;

@Data
public class StringConstant implements CustomConstant {
    private String value;

    public StringConstant(String value) {
        this.value = value;
    }

    public String get() {
        return value;
    }
}
