package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: 堆对象解析测试类
 * Date: 2025/4/2 上午9:03
 **/
public class MyObject {
    public static int staticVar;
    private int instanceVar;

    public static void main(String[] args) {
        int x = 32768;      // ldc
        MyObject myObject = new MyObject();
        MyObject.staticVar = x;     // putstatic
        x = MyObject.staticVar;     // getstatic
        myObject.instanceVar = x;   // putfield
        x = myObject.instanceVar;   // getfield
        Object obj = myObject;
        if (obj instanceof MyObject) {
            myObject = (MyObject) obj;
            System.out.println(myObject.instanceVar);
        }
    }
}
