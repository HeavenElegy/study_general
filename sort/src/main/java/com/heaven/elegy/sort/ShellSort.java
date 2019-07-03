package com.heaven.elegy.sort;

import java.util.Arrays;

/**
 * 希尔排序
 *
 * @author lixiaoxi
 */
public class ShellSort extends InterfaceSort {


    public ShellSort() {

    }


    public static void main(String[] args) {
        InterfaceSort sort = new ShellSort();
        sort.sort();
    }

    @Override
    void doSort() {

        for (int number = stepLengthN2(arr.length); number >= 1; number = stepLengthN2(number)) {

            showByStep(number);
//            System.out.println("----------------------------------");

            // 开始插入排序
            for (int i = number; i < arr.length; i++) {

                int temp = arr[i];

                int j;
                for (j = i; j - number >= 0 && arr[j - number] > temp; j -= number) {
                    arr[j] = arr[j - number];
                }

                arr[j] = temp;
            }

            showByStep(number);
//            System.out.println("==================================");

        }

        ;
    }

    /**
     * 根据步长打印排序矩阵
     *
     * @param stepLength 步长。　不能为０
     */
    private void showByStep(int stepLength) {

        if (stepLength <= 0) {
            throw new IllegalArgumentException("步长不能小于等于０");
        }

        for (int i = 0; i < arr.length; i += stepLength) {

            int showLength = i + stepLength > arr.length ? arr.length : i + stepLength;

//            System.out.println(Arrays.toString(Arrays.copyOfRange(arr, i, showLength)));
        }


    }

    /**
     * 计算步长方法。公式为2^k-1
     */
    private int stepLength2k(int current) {
        int k = 1;
        int last = 0;
        int result = 0;
        int temp;

        while ((temp = (int) (Math.pow(2, k) - 1)) <= current) {
            result = last;
            last = temp;
            k ++;
        }
//        System.out.println("k=" + k + ", last=" + last + ", result=" + result);

        return result;

    }

    private int stepLengthN2(int current) {
        return current/2;
    }
}
