package com.heaven.elegy.sort;


import java.util.Arrays;

/**
 * 堆排序
 *
 * <table>
 *     <tr>
 *         <td><b>类型</b></td>
 *         <td>选择排序</td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度</b></td>
 *         <td>O(nlogn)</td>
 *     </tr>
 *     <tr>
 *         <td><b>稳定性</b></td>
 *         <td>不稳定</td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度的计算</b></td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>描述</b></td>
 *         <td>将数组抽象为<a href="https://zh.wikipedia.org/wiki/%E6%9C%80%E5%A4%A7%E2%80%94%E6%9C%80%E5%B0%8F%E5%A0%86">二叉树</a>，并通过对这个二叉树转换为最大堆的
 *         操作，以此得到最大堆中的最大值(堆顶值)，并将它放置到二叉树末尾。循环这个操作直至堆顶为堆中最小值时，此数组排序结束</td>
 *     </tr>
 *     <tr>
 *         <td><b>内部变化顺序</b></td>
 *         <td>
 *              <ol>
 *              </ol>
 *         </td>
 *     </tr>
 *     <tr>
 *         <td>几个涉及到的公式</td>
 *         <td>
 *             获取左子节点下标: 2n<br>
 *             获取右子节点下标:2n+1<br>
 *             获取父节点下标:n/2
 *         </td>
 *     </tr>
 * </table>
 * @author lixiaoxi
 */
public class HeapSort extends ManagerSort {


    public HeapSort(String[] args) {
        super(args);
    }

    public HeapSort(int length) {
        super(length);
    }

    public HeapSort(int[] arr) {
        super(arr);
    }

    public static void main(String[] args) {
        ManagerSort sort = new HeapSort(100000000);
        sort.sort();
    }

    /**
     * 这里的算法源自<a href="https://zh.wikipedia.org/wiki/%E5%A0%86%E6%8E%92%E5%BA%8F">wiki百科</a>
     * <p>在百科中，对于java描述的部分，分作两步进行处理</p>
     * <p>第一步。初次排序，求最大堆</p>
     * <p>第二部。遍历堆中的所有数据，由后至前，进行置换与求最大堆</p>
     */
    @Override
    void doSort() {

        // 定义了len。注意，这个len是下标而不是实际长度
        int len = arr.length - 1;
        // 第一步，先求一次最大堆。开始位置为最后一个非叶子节点
        // len>>1可以求出最后一个非叶子节点的位置。并以此为初始点，向前遍历所有非叶子节点。将沿路线将每一个非叶子节点进行最大堆处理
        for(int i = len >> 1; i >= 0; i--) {
            maxHeap(i, len);
        }
        // 第二步，进行全量求最大堆，并且在每次执行钱，先将堆顶的商议求出的堆中最大值与堆中最后一个非最大值的数字进行替换
        // 这种方式，只用用于已经满足最大堆条件的堆对象
        for(int i = len; i > 0; i--) {
            swap(0, i);
            maxHeap(0, i - 1);
        }

    }

    /**
     * 最大堆操作
     * <p>操作目标为非叶子节点。</p>
     * <p>自身带有递归操作。当发生值置换时，为了保证当前节点级一下的所有节点符合最大堆特性，会进行递归操作。直至堆末尾</p>
     *
     * @param index 非叶子节点下标
     * @param len   操作范围&有效范围。以下标计数
     */
    private void maxHeap(int index, int len) {

        // 计算左侧叶子节点下标
        int li = (index << 1) + 1;
        // 计算右侧叶子节点下标
        int ri = li + 1;
        // 预定义的最大值下标。取左侧
        int maxi = li;

        // 保证li可到达(变相保证maxi可到达)
        if (li > len) {
            return;
        }

        // 获取子节点中的较大的值的下标
        if (ri <= len && arr[li] < arr[ri]) {
            // 右子节点存在且大于左子节点
            maxi = ri;
        }

        // 将最大的子节点与他的父节点进行比较
        if (arr[index] < arr[maxi]) {
            // 父节点小于子节点中的大值。对父节点进行替换
            swap(index, maxi);
            // 此时小值还是有可能大于当前小值节点中的叶子节点，所以在替换完成后对替换后的子节点进行最大堆操作
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
