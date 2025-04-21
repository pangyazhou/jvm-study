package org.yzpang.chapter02;

import java.util.HashSet;
import java.util.Set;

/**
 * Author: yzpang
 * Desc: 方法区溢出
 * Date: 2025/4/15 上午10:14
 **/
public class MethodMemoryErrorDemo {

    public static void main(String[] args) {
        Set<String> set = new HashSet<String>();
        short i = 0;
        while (true) {
            set.add(String.valueOf(++i).intern());
        }
    }
}
