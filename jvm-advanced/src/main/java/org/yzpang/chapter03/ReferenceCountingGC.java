package org.yzpang.chapter03;

/**
 * Author: yzpang
 * Desc: 引用计数算法
 * Date: 2025/4/15 下午1:36
 **/
public class ReferenceCountingGC {
    public Object instance = null;

    private static final int _1MB = 1024 * 1024;

    private byte[] bigSize = new byte[2 * _1MB];

    public static void testGC() {
        ReferenceCountingGC objA = new ReferenceCountingGC();
        ReferenceCountingGC objB = new ReferenceCountingGC();
        objA.instance = objB;
        objB.instance = objA;
        objA = null;
        objB = null;
        System.gc();
    }

    public static void main(String[] args) throws InterruptedException {
        testGC();
    }
}
