package com.heaven.elegy.sort;

import java.util.Arrays;

/**
 * 插入排序
 * <p>O(n^2)</p>
 *
 * @author lixiaoxi
 */
public class InsertionSort extends InterfaceSort {


    public static void main(String[] args) {
        InterfaceSort sort = new InsertionSort();
        sort.sort();
    }


    @Override
    void doSort() {
        for (int i = 1; i < arr.length; i++) {

            int num = arr[i];
            int j;
            for (j = i; j > 0 && num < arr[j - 1]; j--) {
                arr[j] = arr[j - 1];
            }
            arr[j] = num;
        }
    }
}
