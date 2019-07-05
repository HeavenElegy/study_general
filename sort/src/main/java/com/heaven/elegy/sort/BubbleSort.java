package com.heaven.elegy.sort;

/**
 * <h2>冒泡排序</h2>
 * <table>
 *     <tr>
 *         <td><b>类型</b></td>
 *         <td>交换排序</td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度</b></td>
 *         <td>O(n^2)</td>
 *     </tr>
 *     <tr>
 *         <td><b>稳定性</b></td>
 *         <td>稳定</td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度的计算</b></td>
 *         <td>设: 规模为n,外层迭代变量为i。则外层没外层每迭代一次，则内层迭代n-i-1次。内层总迭代次数为(n-0-1) + (n-1-1) + (n-2-1) + (n-3-1) .... + 1。先进行+1，再进行数列求和得到n(n+2)/2。取最大级n^2。得到此排序算的时间复杂度为O(n^2)</td>
 *     </tr>
 *     <tr>
 *         <td><b>描述</b></td>
 *         <td>对k与k+1进行比较，将两个数中较大的值换到右侧。当k=len-1时即完成了对数组中的最大值的查找与放置，此时k归0并重复此步骤，每次k上限减1</td>
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
        // 外层循环。共循环n次。每循环一次代表内层循环完成对一个最大数的放置
        for (int i = 0; i < arr.length; i++) {

            // 内层循环。范围为0-已排序位置-1。对n=0开始，与n+1所处的两个数字进行比较，并将其中较大这放置到n+1的位置上。
            for (int j = 0; j < arr.length - i -1; j++) {

                // k>k+1时进行互换
                if(arr[j]>arr[j+1]) {
                    int temp = arr[j+1];
                    arr[j+1] = arr[j];
                    arr[j] = temp;
                }
            }
        }
    }
}
