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
    // java/lang/Class 对象绑定的CustomClass
    protected CustomClass extra;
    protected CustomSlots fields;
    protected int hashCode = 0;
    // 堆栈跟踪
    protected CustomStackTraceElement[] stackTrace;

    public CustomObject() {
    }

    public CustomObject(CustomClass clazz) {
        this.clazz = clazz;
        fields = new CustomSlots(clazz.getInstanceSlotCount());
    }

    public boolean isInstanceOf(CustomClass clazz) throws Exception {
        return clazz.isAssignableFrom(this.clazz);
    }

    /**
     * 设置对象引用值
     * @param name 字段名
     * @param descriptor 字段描述符
     * @param refObj 引用值
     */
    public void setRefVar(String name, String descriptor, CustomObject refObj) {
        CustomField field = this.clazz.getField(name, descriptor, false);
        if (field != null) {
            fields.setReference(field.getSlotId(), refObj);
        }
    }

    public CustomObject getRefVar(String name, String descriptor) {
        CustomField field = this.clazz.getField(name, descriptor, false);
        if (field != null) {
            return fields.getReference(field.getSlotId());
        }
        return null;
    }

}
