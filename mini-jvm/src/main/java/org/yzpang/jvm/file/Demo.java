package org.yzpang.jvm.file;

import lombok.Data;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/19 下午4:40
 **/
@Deprecated
@Resource(name = "demo-resource", description = "description class annotation")
public class Demo extends DemoSuper implements DemoInterface, DemoInterface2 {
    private int name;
    @Deprecated
    private int age;
    private final static int tag = 0x53;
    private final static float score = 83.2f;
    private final static long id = 0x123456789L;
    private final static double salary = 0.975;
    private static String label = "LABEL";
    public Demo(int name, int age) {}

    public void say(){
        System.out.println(label);
    }

    public static void main(String[] args) {
        Demo demo = new Demo(1,2);
        demo.say();
        demo.show();
        DemoInterface demo2 = new Demo(2,3);
        demo2.show();
        DemoInterface demo3 = () -> System.out.println("Hello, Lambda!");
        demo3.show();
    }

    public static String run(int name, long score){
        int i = 12;
        long l = 97;
        return "running";
    }

    @Override
    public void show() {
        System.out.println("nothing");
    }

    @Override
    public void go() {
        System.out.println("go");
    }
}
