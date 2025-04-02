package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: 父类
 * Date: 2025/4/2 下午4:57
 **/
public class Animal {
    protected String name;

    public void eat() {
        System.out.println("Animal eat");
    }

    protected final void sleep() {
        System.out.println("Animal sleep");
    }
}
