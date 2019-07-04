package com.heaven.elegy.sort;

import java.util.Arrays;

/**
 * 归并排序
 *
 * @author lixiaoxi
 */
public class MargeSort extends ManagerSort {


    /**
     * 归并排序所用的额外储存空间
     */
    private int[] temp;


    public MargeSort(String[] args) {
        super(args);
    }

    public MargeSort(int length) {
        super(length);
    }

    public MargeSort(int[] arr) {
        super(arr);
    }

    public static void main(String[] args) {
        MargeSort sort = new MargeSort(new int[]{5, 4, 3, 2, 1});
        sort.temp = new int[sort.arr.length];
        sort.sort();
        System.out.println(Arrays.toString(sort.arr));
    }


    @Override
    void doSort() {
        m1(0, arr.length - 1);
    }


    /**
     * 递归方法
     * <p>内部所有内容全部以下标作为基准</p>
     * @param head  开始位置(下标)
     * @param tail  结束位置(下标)
     */
    private void m1(int head, int tail) {

        // 因为是基于head进行偏移运算的。所以这里有可能会在上一次的迭代中出现开始位置小于结束位置的可能。
        if(head >= tail) {
            return;
        }

        // 本次递归需要处理的长度0开始
        int len = tail - head;
        // 中间下标
        int mid = (len >> 1) + head;

        // 左子节点的开始与结束位置
        int start1 = head, end1 = mid;
        // 右子节点的开始与结束位置
        int start2 = mid + 1, end2 = tail;

        System.out.println(Arrays.toString(Arrays.copyOfRange(arr, start1, end1 +1)) + ", " + Arrays.toString(Arrays.copyOfRange(arr, start2, end2 + 1)));

        m1(start1, end1);
        m1(start2, end2);

        // 进入到此行时，当前所迭代的分制两侧均已排序完成。
        int k = head;
        while (start1 <= end1 && start2 <= end2) {
            temp[k++] = arr[start1] < arr[start2]? arr[start1++]:arr[start2++];
        }
        // 进入到此行时，代表当前分制两侧已有一侧为空(完成)
        while (start1 <= end1) {
            temp[k++] = arr[start1++];
        }
        while (start2 <= end2) {
            temp[k++] = arr[start2++];
        }

        // 将临时数组中的数据替换到结果数组中
        for(int i = head; i <= tail; i++) {
            arr[i] = temp[i];
        }


    }


}
