package com.heaven.elegy.sort;

import org.apache.commons.lang3.RandomUtils;


/**
 * @author lixiaoxi
 */
public abstract class InterfaceSort {

    protected int[] arr;
    protected long initData;
    protected long startTime;
    protected long sortData;
    private static final int LENGTH = 10000000;

    public InterfaceSort(int[] arr) {
        this.arr = arr;
        init();
    }

    public InterfaceSort() {
        init();
    }

    protected void sort(){

        System.out.println("开始排序");
        doSort();
        sortData = System.currentTimeMillis();
        System.out.println("排序完成。 用时: " + ((sortData - initData)) + "毫秒");
    }

    private void init() {
        startTime = System.currentTimeMillis();
        if(arr == null) {
            arr = new int[LENGTH];
            for(int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtils.nextInt(0, LENGTH);
            }
            initData = System.currentTimeMillis();
            System.out.println("初始化数据完成。 用时: " + ((initData - startTime)) + "毫秒");
        }
        initData = System.currentTimeMillis();
    }

    abstract void doSort();
}
