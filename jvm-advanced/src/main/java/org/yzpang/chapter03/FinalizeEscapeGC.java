package org.yzpang.chapter03;

/**
 * Author: yzpang
 * Desc: 使用finalize方法逃逸GC
 * Date: 2025/4/17 上午8:06
 **/
public class FinalizeEscapeGC {

    public static FinalizeEscapeGC instance = null;

    public void isAlive() {
        System.out.println("yes, i am still alive");
    }

    @Override
    protected void finalize() throws Throwable {
        super.finalize();
        System.out.println("finalize method executed!");
        FinalizeEscapeGC.instance = this;
    }

    public static void main(String[] args) throws InterruptedException {
        instance = new FinalizeEscapeGC();

        // 对象第一次拯救自己-成功
        instance = null;
        System.gc();

        Thread.sleep(1000);
        if (instance != null) {
            instance.isAlive();
        } else {
            System.out.println("no, i am dead");
        }

        // 对象第二次拯救自己-失败
        instance = null;
        System.gc();
        Thread.sleep(1000);
        if (instance != null) {
            instance.isAlive();
        }  else {
            System.out.println("no, i am dead");
        }
    }
}
