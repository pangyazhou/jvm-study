package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: 字符串拼接
 * Date: 2025/4/10 上午8:48
 **/
public class ConcatStringTest {
    public static void main(String[] args) {
        String str1 = "abc1";
        String str2 = "abc1";
        System.out.println(str1 == str2);
        int x = 1;
        String str3 = "abc" + x;
        System.out.println(str1 == str3);
        str3 = str3.intern();
        System.out.println(str1 == str3);
    }
}
