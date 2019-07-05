package com.heaven.elegy.sort;

/**
 * <h2>格式模板</h2>
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
public abstract class TamplateSort extends ManagerSort{
    public TamplateSort(String[] args) {
        super(args);
    }

    public TamplateSort(int length) {
        super(length);
    }

    public TamplateSort(int[] arr) {
        super(arr);
    }
}
