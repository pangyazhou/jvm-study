package org.yzpang.jvm.runtimedata;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 局部变量表中的槽 Slot
 * Date: 2025/3/25 上午11:16
 **/
@Data
public class CustomSlot {
    // 基本类型
    private int intValue;
    // 引用类型
    private Object reference;
}
