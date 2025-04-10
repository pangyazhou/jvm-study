package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: clone 本地方法测试
 * Date: 2025/4/10 上午11:23
 **/
public class CloneTest implements Cloneable {
    private double pi = 3.1415;

    @Override
    protected CloneTest clone() throws CloneNotSupportedException {
        return (CloneTest) super.clone();
    }

    public static void main(String[] args) throws CloneNotSupportedException {
        /*CloneTest obj1 = new CloneTest();
        CloneTest obj2 = (CloneTest) obj1.clone();
        obj1.pi = 3.1415926;
        System.out.println(obj1.pi);
        System.out.println(obj2.pi);*/
        /*CloneTest[] objs = new CloneTest[3];
        objs[0] = new CloneTest();
        objs[1] = new CloneTest();
        objs[2] = new CloneTest();
        CloneTest[] cloneTests = objs.clone();
        System.out.println(cloneTests.length);*/
        int[] ints = new int[10];
        int[] clone = ints.clone();
        System.out.println(clone[2]);
    }
}
