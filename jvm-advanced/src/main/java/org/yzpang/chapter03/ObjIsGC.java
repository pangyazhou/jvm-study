package org.yzpang.chapter03;

import java.lang.ref.PhantomReference;
import java.lang.ref.ReferenceQueue;
import java.lang.ref.WeakReference;

/**
 * Author: yzpang
 * Desc: 判断对象是否被GC回收
 * Date: 2025/4/17 上午8:17
 **/
public class ObjIsGC {
    public static void main(String[] args) throws InterruptedException {
//        weakGC();
        phantomGC();
    }

    /**
     * 弱引用 判断
     */
    public static void weakGC() throws InterruptedException {
        ObjIsGC obj = new ObjIsGC();
        // 弱引用可以被GC回收
        System.out.println(obj);
        WeakReference<ObjIsGC> weakRef = new WeakReference<>(obj);
        System.out.println(weakRef.get());  // 可以访问

        obj = null;
        System.gc();
        Thread.sleep(1000);

        System.out.println(weakRef.get());  // obj被GC回收后,返回null
    }

    /**
     * 虚引用 判断
     */
    public static void phantomGC() throws InterruptedException {
        ReferenceQueue<ObjIsGC> refQueue = new ReferenceQueue<>();
        ObjIsGC obj = new ObjIsGC();
        PhantomReference<ObjIsGC> phantomRef = new PhantomReference<>(obj, refQueue);

        System.out.println("Before GC: " + phantomRef.get());

        obj = null;
        System.gc();

        Thread.sleep(500);

        System.out.println("After GC: " + phantomRef.get());

        if (refQueue.poll() != null) {
            System.out.println("Object has been GC'ed");
        } else {
            System.out.println("Object has not been GC'ed");
        }
    }
}
