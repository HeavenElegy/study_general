package com.heaven.elegy.sort;


/**
 * <h2>希尔排序</h2>
 * <table>
 *     <tr>
 *         <td><b>类型</b></td>
 *         <td>插入排序</td>
 *     </tr>
 *     <tr>
 *         <td><b>时间复杂度</b></td>
 *         <td>n/i^2=O(n^2), 2^k-1=O(n^3/2), 2^i3^j=O(nlogn)</td>
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
 *         <td></td>
 *     </tr>
 *     <tr>
 *         <td><b>内部变化顺序</b></td>
 *         <td>
 *              <ol>
 *              </ol>
 *         </td>
 *     </tr>
 * </table>
 * @author lixiaoxi
 */
public class ShellSort extends ManagerSort {


    public ShellSort(String[] args) {
        super(args);
    }

    public static void main(String[] args) {
        ManagerSort sort = new ShellSort(args);
        sort.sort();
    }

    @Override
    void doSort() {

        for (int number = stepLength2k(arr.length); number >= 1; number = stepLength2k(number)) {

            showByStep(number);
//            System.out.println("----------------------------------");

            // 开始插入排序
            for (int i = number; i < arr.length; i++) {

                int temp = arr[i];

                int j;
                for (j = i; j - number >= 0 && arr[j - number] > temp; j -= number) {
                    arr[j] = arr[j - number];
                }

                arr[j] = temp;
            }

            showByStep(number);
//            System.out.println("==================================");

        }

        ;
    }

    /**
     * 根据步长打印排序矩阵
     *
     * @param stepLength 步长。　不能为０
     */
    private void showByStep(int stepLength) {

        if (stepLength <= 0) {
            throw new IllegalArgumentException("步长不能小于等于０");
        }

        for (int i = 0; i < arr.length; i += stepLength) {

            int showLength = i + stepLength > arr.length ? arr.length : i + stepLength;

//            System.out.println(Arrays.toString(Arrays.copyOfRange(arr, i, showLength)));
        }


    }

    /**
     * 计算步长方法。公式为2^k-1
     */
    private int stepLength2k(int current) {
        int k = 1;
        int last = 0;
        int result = 0;
        int temp;

        while ((temp = (int) (Math.pow(2, k) - 1)) <= current) {
            result = last;
            last = temp;
            k ++;
        }
//        System.out.println("k=" + k + ", last=" + last + ", result=" + result);

        return result;

    }

    private int stepLengthN2(int current) {
        return current/2;
    }
}
