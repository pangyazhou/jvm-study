package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/4/8 上午11:03
 **/
public class GetClassTest {
    public static void main(String[] args) {
        System.out.println(void.class.getName());
        System.out.println(boolean.class.getName());
        System.out.println(byte.class.getName());
        System.out.println(short.class.getName());
        System.out.println(int.class.getName());
        System.out.println(float.class.getName());
        System.out.println(long.class.getName());
        System.out.println(double.class.getName());
        System.out.println(Object.class.getName());
        System.out.println(int[].class.getName());
        System.out.println(int[][].class.getName());
        System.out.println(Object[].class.getName());
        System.out.println(Object[][].class.getName());
        System.out.println(Runnable.class.getName());
        System.out.println("abc".getClass().getName());
    }
}
