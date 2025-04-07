package org.yzpang.jvm.file;

/**
 * Author: yzpang
 * Desc:
 * Date: 2025/4/7 上午8:41
 **/
public class ArrayDemo {
    public static void main(String[] args) {
        int[] a1 = new int[10];         // newarray
        Object[] a2 = new Object[10];   // anewarray
        int[][] a3 = new int[10][10];   // multianewarray
        int x = a1.length;              // arraylength
        a1[0] = 100;                    // iastore
        int y = a1[0];                  // iaload
        a2[0] = new Object();           // aastore
        Object o = a2[0];               // aaload
        a3[0][0] = 10;
        int z = a3[0][0];
        int [][][] a4 = new int[3][4][5];
    }
}
