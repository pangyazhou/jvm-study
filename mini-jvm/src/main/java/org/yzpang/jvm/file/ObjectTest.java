package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: Object本地方法测试
 * Date: 2025/4/10 上午10:25
 **/
public class ObjectTest {
    public static void main(String[] args) {
        ObjectTest obj1 = new ObjectTest();
        ObjectTest obj2 = new ObjectTest();
        System.out.println(Integer.toHexString(obj1.hashCode()));
        System.out.println(obj1.toString());
        System.out.println(obj1.equals(obj2));
        System.out.println(obj1.equals(obj1));
    }
}
