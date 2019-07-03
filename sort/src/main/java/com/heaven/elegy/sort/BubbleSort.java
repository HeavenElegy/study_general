package com.heaven.elegy.sort;

import java.util.Arrays;

/**
 * 冒泡排序
 * <p>时间复杂度O(n^2)</p>
 *
 * @author lixiaoxi
 */
public class BubbleSort extends InterfaceSort {



    public static void main(String[] args) {
        InterfaceSort sort = new BubbleSort();
        sort.sort();
    }


    @Override
    public void doSort() {
        for (int i = 0; i < arr.length; i++) {
            for (int j = 0; j < arr.length - i -1; j++) {
                if(arr[j]>arr[j+1]) {
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
