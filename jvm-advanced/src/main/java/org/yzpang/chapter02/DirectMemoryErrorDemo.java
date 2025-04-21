package org.yzpang.chapter02;

/**
 * Author: yzpang
 * Desc: 直接内存溢出
 * Date: 2025/4/15 上午10:27
 **/
public class DirectMemoryErrorDemo {
    private static final int _1MB = 1024 * 1024;

    public static void main(String[] args) throws IllegalAccessException {
        /*Field field = Unsafe.class.getDeclaredFields()[0];
        field.setAccessible(true);
        Unsafe unsafe = (Unsafe) field.get(null);
        while (true) {
            unsafe.allocateMemory(_1MB);
        }*/
    }
}
