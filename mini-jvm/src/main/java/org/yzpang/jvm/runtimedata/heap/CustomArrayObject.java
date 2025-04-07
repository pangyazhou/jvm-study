package org.yzpang.jvm.runtimedata.heap;


import lombok.Data;

import java.lang.reflect.Array;

/**
 * Author: yzpang
 * Desc: 数组对象
 * Date: 2025/4/7 上午7:52
 **/
@Data
public class CustomArrayObject extends CustomObject{
    private Object array;

    public CustomArrayObject(CustomClass clazz, Class<?> arrayType, int count) {
        array = Array.newInstance(arrayType, count);
        this.clazz = clazz;
    }

    public CustomArrayObject(CustomClass clazz, Object array) {
        this.clazz = clazz;
        this.array = array;
    }

    public byte[] getBytes(){
        return (byte[]) array;
    }

    public char[] getChars(){
        return (char[]) array;
    }

    public short[] getShorts(){
        return (short[]) array;
    }

    public int[] getInts(){
        return (int[]) array;
    }

    public long[] getLongs(){
        return (long[]) array;
    }

    public float[] getFloats(){
        return (float[]) array;
    }

    public double[] getDoubles(){
        return (double[]) array;
    }

    public CustomObject[] getRefs(){
        return (CustomObject[]) array;
    }

    public int arrayLength(){
        return Array.getLength(array);
    }

}
