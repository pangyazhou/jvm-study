package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/4/3 下午2:27
 **/
public class FibonacciTest {
    public static void main(String[] args) {
        long x = fibonacci(30);
        System.out.println(x);
    }

    private static long fibonacci(long n) {
        if (n <= 1) {
            return n;
        }
        return fibonacci(n - 1) + fibonacci(n - 2);
    }
}
