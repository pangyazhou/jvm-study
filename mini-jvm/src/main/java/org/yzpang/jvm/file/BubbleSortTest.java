package org.yzpang.jvm.file;

import java.util.Arrays;

/**
 * Author: yzpang
 * Desc: 冒泡排序测试
 * Date: 2025/4/7 下午2:52
 **/
public class BubbleSortTest {
    public static void main(String[] args) {
        int[] arr = {22, 84, 77, 11, 95, 9, 78, 56, 36, 97, 65, 36, 10, 24, 92};
        bubbleSort(arr);
    }

    private static void bubbleSort(int[] arr) {
        boolean flag = true;
        int j = 0;
        int temp;
        while (flag) {
            flag = false;
            j++;
            for (int i = 0; i < arr.length - j; i++) {
                if (arr[i] > arr[i + 1]) {
                    temp = arr[i];
                    arr[i] = arr[i+1];
                    arr[i+1] = temp;
                    flag = true;
                }
            }
        }
    }


}
