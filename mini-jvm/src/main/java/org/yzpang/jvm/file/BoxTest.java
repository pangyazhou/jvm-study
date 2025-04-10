package org.yzpang.jvm.file;

import java.util.ArrayList;

/**
 * Author: yzpang
 * Desc: 自动装箱测试
 * Date: 2025/4/10 下午4:05
 **/
public class BoxTest {

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);
        System.out.println(list.toString());
        for (int i : list) {
            System.out.println(i);
        }
    }
}
