package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 方法描述符结构
 * Date: 2025/4/2 下午3:35
 **/
@Data
public class CustomMethodDescriptor {
    private String[] parameterTypes;
    private String returnType;

    public CustomMethodDescriptor(String[] parameterTypes, String returnType) {
        this.parameterTypes = parameterTypes;
        this.returnType = returnType;
    }
}
