package com.heaven.elegy.sort;

/**
 * 选择排序
 * <p>O(n^2)</p>
 *
 * @author lixiaoxi
 */
public class SelectionSort extends ManagerSort {

    public SelectionSort(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        ManagerSort sort = new SelectionSort(args);
        sort.sort();
    }

    @Override
    void doSort() {
        for (int i = 0; i < arr.length; i++) {
            int min = i;
            for (int j = i + 1; j < arr.length; j++) {
                if (arr[j] < arr[min]) {
                    min = j;
                }
            }

            if(min != i) {
                int temp = arr[i];
                arr[i] = arr[min];
                arr[min] = temp;
            }

        }
    }
}