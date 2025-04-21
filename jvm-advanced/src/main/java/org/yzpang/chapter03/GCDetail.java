package org.yzpang.chapter03;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

/**
 * Author: yzpang
 * Desc: 查看垃圾收集器
 * Date: 2025/4/17 上午10:04
 **/
public class GCDetail {
    public static void main(String[] args) {
        List<GarbageCollectorMXBean> garbageCollectorMXBeans = ManagementFactory.getGarbageCollectorMXBeans();
        for (GarbageCollectorMXBean gc : garbageCollectorMXBeans) {
            System.out.println(gc.getName());
        }
    }
}
