package org.yzpang.jvm.runtimedata;

import lombok.Data;
import org.yzpang.jvm.runtimedata.heap.CustomObject;

/**
 * Author: yzpang
 * Desc: 局部变量表中的槽 Slot
 * Date: 2025/3/25 上午11:16
 **/
@Data
public class CustomSlot implements Cloneable{
    // 基本类型
    private int intValue;
    // 引用类型
    private CustomObject reference;

    public void clear(){
        intValue = 0;
        reference = null;
    }

    @Override
    public CustomSlot clone() {
        try {
            return (CustomSlot) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
