package org.yzpang.jvm.runtimedata.heap;

import lombok.Data;
import org.yzpang.jvm.runtimedata.CustomSlots;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/4/1 上午11:15
 **/
@Data
public class CustomObject {
    protected CustomClass clazz;
    protected CustomSlots fields;

    public CustomObject(CustomClass clazz) {
        this.clazz = clazz;
        fields = new CustomSlots(clazz.getInstanceSlotCount());
    }

    public boolean isInstanceOf(CustomClass clazz) {
        return clazz.isAssignableFrom(this.clazz);
    }


}
