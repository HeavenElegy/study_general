package com.heaven.elegy.sort;

import java.util.Arrays;

/**
 * @author lixiaoxi
 */
public class HeapSort extends InterfaceSort {



    public static void main(String[] args) {
        InterfaceSort sort = new HeapSort();
        sort.sort();
//        System.out.println(Arrays.toString(sort.arr));
    }

    @Override
    void doSort() {

        // 这里定义的len。
        int len = arr.length - 1;
        // 初次排序
        for(int i = len >> 1; i >=  0; i--) {
            maxHeap(i, len);
        }

        for(int i = len; i > 0; i--) {
            swap(0, i);
            maxHeap(0, i - 1);
        }

    }

    /**
     * 最大堆操作
     *
     * @param index 节点下表
     * @param len   长度
     */
    private void maxHeap(int index, int len) {

        int li = (index << 1) + 1;
        int ri = li + 1;
        int maxi = li;

        // 保证li可到达(变相保证maxi可到达)
        if (li > len) {
            return;
        }

        // 获取子节点中的较大的值的下标
        if (ri < len && arr[li] < arr[ri]) {
            // 右子节点存在且大于左子节点
            maxi = ri;
        }

        // 将最大的子节点与他的父节点进行比较
        if (arr[index] < arr[maxi]) {
            // 父节点小于子节点中的大值。对父节点进行替换
            swap(index, maxi);
            // 替换完成后对替换后的子节点进行最大堆操作
            maxHeap(maxi, len);
        }
    }


    /**
     * 根据下标对数组内的两个元素进行替换
     */
    private void swap(int a, int b) {
        int temp = arr[a];
        arr[a] = arr[b];
        arr[b] = temp;
    }


}
