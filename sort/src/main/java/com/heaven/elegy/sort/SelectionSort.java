package com.heaven.elegy.sort;

/**
 * <h2>选择排序</h2>
 * <table>
 *     <tr>
 *         <td><b>类型</b></td>
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度</b></td>
 *         <td>O(n^2)</td>
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
