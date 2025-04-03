package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: 方法调用与返回指令测试类
 * Date: 2025/4/3 下午1:49
 **/
public class InvokeDemo implements Runnable{

    public static void main(String[] args) {
        new InvokeDemo().test();
    }

    public void test(){
        InvokeDemo.staticMethod();                      // invoke_static
        InvokeDemo demo = new InvokeDemo();             // invoke_special
        demo.instanceMethod();                          // invoke_special
        super.equals(null);                             // invoke_special
        this.run();                                     // invoke_virtual
        ((Runnable)demo).run();                         // invoke_interface
    }

    public static void staticMethod(){

    }

    private void instanceMethod(){

    }

    @Override
    public void run() {
    }
}
