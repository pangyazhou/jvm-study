package org.yzpang.jvm.file;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/4/3 上午10:03
 **/
public class Cat{

    public static void main(String[] args) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Dog dog = new Dog();
        Method sleep = Animal.class.getDeclaredMethod("sleep");
        sleep.setAccessible(true);
        sleep.invoke(dog);
    }
}
