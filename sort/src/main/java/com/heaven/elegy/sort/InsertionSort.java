package com.heaven.elegy.sort;

/**
 * 插入排序
 * <p>O(n^2)</p>
 *
 * @author lixiaoxi
 */
public class InsertionSort extends ManagerSort {


    public InsertionSort(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        ManagerSort sort = new InsertionSort(args);
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
