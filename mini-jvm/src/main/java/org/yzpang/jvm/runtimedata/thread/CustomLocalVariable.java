package org.yzpang.jvm.runtimedata.thread;

import lombok.Data;

/**
 * Author: yzpang
 * Desc: 本地变量表
 * Date: 2025/3/25 上午11:11
 **/
@Data
public class CustomLocalVariable {
    private int maxLocals;
    private CustomSlot[] slots;

    public CustomLocalVariable(int maxLocals) {
        if (maxLocals > 0) {
            this.maxLocals = maxLocals;
            this.slots = new CustomSlot[maxLocals];
            for (int i = 0; i < maxLocals; i++) {
                slots[i] = new CustomSlot();
            }
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
    public void setReference(int index, Object value) {
        slots[index].setReference(value);
    }
    public Object getReference(int index) {
        return slots[index].getReference();
    }
}
