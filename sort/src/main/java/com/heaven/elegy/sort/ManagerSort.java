package com.heaven.elegy.sort;

import org.apache.commons.lang3.RandomUtils;


/**
 * 排序基类。用以定义一些行为规范
 * @author lixiaoxi
 */
public abstract class ManagerSort {

    /**
     * 待排序数组
     */
    protected int[] arr;
    /**
     * 开始时间
     */
    protected long startTime;
    /**
     * 初始化完成时间
     */
    protected long initData;
    /**
     * 排序完成时间
     */
    protected long sortData;
    /**
     * 初始化数组长度
     */
    private int length = 10000;

    /**
     * 构造方法，自动初始化待排序数组长度。要用于实现类的main函数
     * @param args  来自main函数参数
     */
    public ManagerSort(String[] args) {
        if(args == null || args.length < 1) {
            throw new IllegalArgumentException("请输入一个数字作为排序目标长度");
        }

        String arg0 = args[0];

        try {
            length = Integer.parseInt(arg0);
        }catch (Throwable e) {
            e.printStackTrace();
            throw new IllegalStateException("初始化目标数组长度失败", e);
        }

        init();
    }

    /**
     * 构造方法，自动初始化待排序数组长度。
     * @param length    待排序数组长度
     */
    public ManagerSort(int length) {
        this.length = length;
        init();
    }

    /**
     * 构造方法，使用指定数组进行初始化
     * @param arr
     */
    public ManagerSort(int [] arr) {
        this.arr = arr;
        init();
    }

    /**
     * 开始执行排序
     */
    public void sort(){

        System.out.println("开始排序");
        doSort();
        sortData = System.currentTimeMillis();
        System.out.println("排序完成。 用时: " + ((sortData - initData)) + "毫秒");
    }

    /**
     * 初始化各数据
     */
    private void init() {
        startTime = System.currentTimeMillis();
        if(arr == null) {
            arr = new int[length];
            for(int i = 0; i < arr.length; i++) {
                arr[i] = RandomUtils.nextInt(0, length);
            }
            initData = System.currentTimeMillis();
            System.out.println("初始化数据完成。 用时: " + ((initData - startTime)) + "毫秒");
        }
        initData = System.currentTimeMillis();
    }

    /**
     * 排序实现
     */
    abstract void doSort();
}
