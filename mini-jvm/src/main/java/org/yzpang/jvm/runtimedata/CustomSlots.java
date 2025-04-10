package org.yzpang.jvm.runtimedata;

import org.yzpang.jvm.runtimedata.heap.CustomObject;

/**
 * Author: yzpang
 * Desc: 存放变量的槽
 * Date: 2025/4/1 上午11:17
 **/
public class CustomSlots {
    protected CustomSlot[] slots;

    public CustomSlots(int count) {
        this.slots = new CustomSlot[count];
        for (int i = 0; i < count; i++) {
            slots[i] = new CustomSlot();
        }
    }

    public void setInt(int index, int value) {
        slots[index].setIntValue(value);
    }
    public int getInt(int index) {
        return slots[index].getIntValue();
    }
    public void setFloat(int index, float value) {
        setInt(index, Float.floatToIntBits(value));
    }
    public float getFloat(int index) {
        return Float.intBitsToFloat(getInt(index));
    }
    public void setLong(int index, long value) {
        slots[index].setIntValue((int)value);
        slots[index+1].setIntValue((int)(value>>>32));
    }
    public long getLong(int index) {
        return (slots[index].getIntValue() & 0xFFFFFFFFL) | ((long)slots[index+1].getIntValue() << 32);
    }
    public void setDouble(int index, double value) {
        setLong(index,Double.doubleToLongBits(value));
    }
    public double getDouble(int index) {
        return Double.longBitsToDouble(getLong(index));
    }
    public void setReference(int index, CustomObject value) {
        slots[index].setReference(value);
    }
    public CustomObject getReference(int index) {
        return slots[index].getReference();
    }

    public void setSlot(int index, CustomSlot slot) {
        slots[index] = slot;
    }

    public int length() {
        return slots.length;
    }

    public CustomSlots clone() {
        CustomSlots clone = new CustomSlots(slots.length);
        for (int i = 0; i < length(); i++) {
            clone.setSlot(i, slots[i].clone());
        }
        return clone;
    }
}
