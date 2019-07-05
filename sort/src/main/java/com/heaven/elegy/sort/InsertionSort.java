package com.heaven.elegy.sort;

/**
 * <h2>插入排序</h2>
 * <table>
 *     <tr>
 *         <td><b>类型</b></td>
 *         <td>插入排序</td>
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
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>描述</b></td>
 *         <td>设下标k>=1。先缓存k的值,当k的值与小于k-1的值时，将k-1的值存入k。直至k等于0。则完成本次k的比较。k最终应等于数组长度减1。标示完成排序</td>
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

        // 外层循环，控制k的起始位置
        for (int i = 1; i < arr.length; i++) {
            // 缓存k的值
            int num = arr[i];
            // 从k向前进行比较。当k的值小于k-n的值时，进入方法体并进行替换
            int j;
            for (j = i; j > 0 && num < arr[j - 1]; j--) {
                arr[j] = arr[j - 1];
            }
            // 当产生变化时有用，在上面的j在替换后会自增1，代表之前的已经无效的位置
            arr[j] = num;
        }
    }
}
