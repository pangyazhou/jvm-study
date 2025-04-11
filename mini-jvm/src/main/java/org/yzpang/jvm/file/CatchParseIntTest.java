package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc: exception测试类
 * Date: 2025/4/11 下午1:53
 **/
public class CatchParseIntTest {

    public static void main(String[] args) {
        String[] params = new String[]{"go", "34"};
        foo(args);
    }

    private static void foo(String[] args) {
        try {
            bar(args);
        } catch (NumberFormatException e) {
            System.out.println("NumberFormatException: " + e.getMessage());
        }
    }

    private static void bar(String[] args) {
        if (args.length == 0) {
            throw new IndexOutOfBoundsException("args is empty");
        }
        int x = Integer.parseInt(args[0]);
        System.out.println(x);
    }
}
