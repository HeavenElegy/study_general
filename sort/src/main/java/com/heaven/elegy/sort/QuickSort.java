package com.heaven.elegy.sort;

import java.util.Arrays;

/**
 * <h2>快速排序</h2>
 * <table>
 *     <tr>
 *         <td><b>类型</b></td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度</b></td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>稳定性</b></td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度的计算</b></td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>描述</b></td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>内部变化顺序</b></td>
 *         <td>
 *              <ol>
 *                  <li>3,2,1</li>
 *                  <li>2,3,1</li>
 *                  <li>2,1,3</li>
 *                  <li>1,2,3</li>
 *              </ol>
 *         </td>
 *     </tr>
 * </table>
 *
 * @author lixiaoxi
 */
public class QuickSort extends ManagerSort {

    public QuickSort(String[] args) {
        super(args);
    }

    public QuickSort(int length) {
        super(length);
    }

    public QuickSort(int[] arr) {
        super(arr);
    }

    public static void main(String[] args) {
        QuickSort sort = new QuickSort(args);
        sort.sort();
//        System.out.println(Arrays.toString(sort.arr));
    }

    @Override
    void doSort() {
        m1(0, arr.length - 1);
    }


    /**
     * 以递归方式进行
     * <p>参考wiki中的代码示例:<a>https://zh.wikipedia.org/wiki/%E5%BF%AB%E9%80%9F%E6%8E%92%E5%BA%8F#Java</a></p>
     *
     * @param head 起始下标
     * @param tail 终止下标
     */
    private void m1(int head, int tail) {

        if (head >= tail) {
            return;
        }

        // 储存迭代变量 - 左侧下标
        int i = head;
        // 储存迭代变量 - 右侧下标
        int j = tail;
        // 中间下标对应的值
        int pivot = arr[(head + tail) / 2];

        // 当i>j时标示分制已完成。否则标示未开始或进行中
        while (i <= j) {

            // 从左至右，尝试找到左侧大于pivot的值的坐标
            while (arr[i] < pivot) {
                ++i;
            }
            // 从右至左，尝试找到右侧小于pivot的值的坐标
            while (arr[j] > pivot) {
                --j;
            }

            // i > j则表示距pivot最近的一个大于pivot的坐标i已经位于距pivot最近的一个小于pivot的值的坐标j的右侧。此时分制已经完成。
            if (i < j) {
                // i < j标示pivot的左方向或右方向有小于或大于pivot的值。发现此种值时，应进行替换
                // 这里的替换有两种情况。标示标示
                // 1. i或j已经在pivot的位置，标示其左侧或右侧存在大于或小于pivot的值
                // 2. i或j均不在pivot的位置，此时标示pivot两侧分别存在一个大于和一个小于pivot的值
                // 此时，无论是i或j与pivot替换，还是i与j相互替换。都将达到本排序的目的。即，以pivot为标准，将数组分割为左侧小于pivot与右侧大于pivot的目的
                // 而在上面的情况2中。情况有些特殊，pivot参与了替换
                int temp = arr[i];
                arr[i] = arr[j];
                arr[j] = temp;

                // 这里对下表i与j进行手动变化。使其进入到下一个状态
                ++i;
                --j;

            } else if (i == j) {
                // pivot为最终中间值时会出现这种情况。
                // 这里对i进行手动加1。这样可以使后续的迭代调用时的传参进行统一
                ++i;
            }
        }

        // 运行到此处时，基于本轮pivot的分片已经完成。进行对子片的再次分片
        // 左侧
        m1(head, j);
        // 右侧
        m1(i, tail);
    }

}
