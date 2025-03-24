package org.yzpang.jvm.file;

import sun.applet.AppletClassLoader;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/24 上午11:27
 **/
public class DemoTest {

    static class DeadLoopClass{
        static {
            System.out.println("hello,world");
        }

    }


    public static void main(String[] args) {
        DeadLoopClass deadLoopClass = new DeadLoopClass();
        ClassLoader classLoader = deadLoopClass.getClass().getClassLoader();
        System.out.println(classLoader);
        Object object = new Object();
        System.out.println(object.getClass().getClassLoader());
    }
}
