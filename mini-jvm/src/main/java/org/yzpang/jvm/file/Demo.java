package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/3/19 下午4:40
 **/
@Deprecated
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

    @Deprecated
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

    @Override
    public void show() {
        System.out.println("nothing");
    }

    @Override
    public void go() {
        System.out.println("go");
    }
}
