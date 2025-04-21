package org.yzpang.chapter02;

import java.util.ArrayList;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 内存溢出异常
 * Date: 2025/4/15 上午9:13
 **/
public class HeapErrorDemo {
    static class OOMObject {}

    public static void main(String[] args) {
        heapOOM();
    }


    /**
     * Java堆溢出
     */
    public static void heapOOM() {
        List<OOMObject> list = new ArrayList<>();
        while (true) {
            list.add(new OOMObject());
        }
    }

}
