package org.yzpang.jvm.runtimedata.thread;

import lombok.Data;
import org.yzpang.jvm.runtimedata.CustomSlot;
import org.yzpang.jvm.runtimedata.heap.CustomObject;

/**
 * Author: yzpang
 * Desc: 操作数栈
 * Date: 2025/3/25 上午11:12
 **/
@Data
public class CustomOperandStack {
    private int maxStack;
    private int size;
    private CustomSlot[] slots;

    public CustomOperandStack(int maxStack) {
        if (maxStack > 0) {
            this.maxStack = maxStack;
            slots = new CustomSlot[maxStack];
            for (int i = 0; i < slots.length; i++) {
                slots[i] = new CustomSlot();
            }
        }
    }

    public void pushInt(int value) {
        slots[size].setIntValue(value);
        size++;
    }
    public int popInt() {
        size--;
        int value = slots[size].getIntValue();
        slots[size].clear();
        return value;
    }
    public void pushFloat(float value) {
        pushInt(Float.floatToIntBits(value));
    }
    public float popFloat() {
        return Float.intBitsToFloat(popInt());
    }
    public void pushLong(long value) {
        slots[size].setIntValue((int)value);
        slots[size+1].setIntValue((int) (value >>> 32));
        size += 2;
    }
    public long popLong() {
        size -= 2;
        long value = (slots[size].getIntValue() & 0XFFFFFFFFL) | ((long) slots[size+1].getIntValue() << 32);
        slots[size].clear();
        slots[size+1].clear();
        return value;
    }
    public void pushDouble(double value) {
        pushLong(Double.doubleToLongBits(value));
    }
    public double popDouble() {
        return Double.longBitsToDouble(popLong());
    }
    public void pushReference(CustomObject value) {
        slots[size].setReference(value);
        size++;
    }
    public CustomObject popReference() {
        size--;
        CustomObject obj =  slots[size].getReference();
        slots[size].clear();
        return obj;
    }

    public void pushSlot(CustomSlot slot) {
        slots[size] = slot;
        size += 1;
    }

    public CustomSlot popSlot() {
        size--;
        return slots[size];
    }

    public CustomObject getRefFromTop(int index) {
        return slots[size - index - 1].getReference();
    }

    public void pushBoolean(boolean value) {
        pushInt(value ? 1 : 0);
    }

    public boolean popBoolean() {
        return popInt() == 1;
    }

    public void clear() {
        size = 0;
        for (CustomSlot slot : slots) {
            slot.clear();
        }
    }
}
