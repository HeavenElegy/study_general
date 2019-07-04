package com.heaven.elegy.sort;

/**
 * 冒泡排序
 * <p>时间复杂度O(n^2)</p>
 *
 * @author lixiaoxi
 */
public class BubbleSort extends ManagerSort {


    public BubbleSort(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        ManagerSort sort = new BubbleSort(args);
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
