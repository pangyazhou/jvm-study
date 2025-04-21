package org.yzpang.chapter03;

/**
 * Author: yzpang
 * Desc: 新建对象分配
 * VM参数: -verbose:gc -Xms20m -Xmx20m -Xmn10m -XX:+PrintGCDetails -XX:SurvivorRatio=8
 * Date: 2025/4/17 上午10:49
 **/
public class ObjectAllocation {
    private static final int _1MB = 1024 * 1024;

    public static void testAllocation() {
        byte[] allocation1, allocation2, allocation3, allocation4, allocation5;
        allocation1 = new byte[2 * _1MB];
        allocation2 = new byte[2 * _1MB];
        allocation3 = new byte[2 * _1MB];
        allocation4 = new byte[2 * _1MB];
        allocation5 = new byte[4 * _1MB];
    }

    public static void main(String[] args) {
        testAllocation();
    }
}
