package org.yzpang.jvm.runtimedata.heap;

import org.yzpang.jvm.runtimedata.CustomSlots;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 可克隆对象
 * Date: 2025/4/10 上午10:54
 **/
public class CustomObjectClone {

    public static CustomObject cloneObj(CustomObject obj) {
        CustomArrayObject object = new CustomArrayObject();
        object.setClazz(obj.getClazz());
        String name = obj.getClazz().getName();
        if (name.startsWith("[")) {
            CustomArrayObject arrayObject =  (CustomArrayObject) obj;
            switch (name) {
                case "[Z":
                case "[B":
                    byte[] bytes = arrayObject.getBytes();
                    object.setArray(Arrays.copyOf(bytes, bytes.length));
                    return object;
                case "[S":
                    short[] shorts = arrayObject.getShorts();
                    object.setArray(Arrays.copyOf(shorts, shorts.length));
                    return object;
                case "[C":
                    char[] chars = arrayObject.getChars();
                    object.setArray(Arrays.copyOf(chars, chars.length));
                    return object;
                case "[I":
                    int[] ints = arrayObject.getInts();
                    object.setArray(Arrays.copyOf(ints, ints.length));
                    return object;
                case "[J":
                    long[] longs = arrayObject.getLongs();
                    object.setArray(Arrays.copyOf(longs, longs.length));
                    return object;
                case "[F":
                    float[] floats = arrayObject.getFloats();
                    object.setArray(Arrays.copyOf(floats, floats.length));
                    return object;
                case "[D":
                    double[] doubles = arrayObject.getDoubles();
                    object.setArray(Arrays.copyOf(doubles, doubles.length));
                    return object;
                default:
                    CustomObject[] refs = arrayObject.getRefs();
                    object.setArray(Arrays.copyOf(refs, refs.length));
                    return object;
            }
        }
        CustomSlots slots = obj.getFields();
        object.setFields(slots.clone());
        return object;
    }
}
