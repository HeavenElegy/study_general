package com.heaven.elegy.common.util;

/**
 * 用于检视java关键字volatile的相关功能
 * <p>配合javap进行反汇编，检查指令是否有不同之处</p>
 * <p>竟态触发暂时不做测试。理论上在多线程的i情况下必定出现</p>
 *
 * @author lixiaoxi
 *
 * @see java.util.Vector
 */
public class VolatileInfo {

    /**
     * 普通竟态变量
     */
    private int commonSum;

    /**
     * 使用了轻量级同步索volatile修饰的普通变量
     */
    private volatile int volatileNum;


    /**
     * 普通方法
     * <p>只对普通变量进行操作</p>
     */
    public void commonMethod() {
        commonSum++;
    }

    /**
     * 测试目标方法
     * <p>只对添加了轻量级同步索volatile修饰的变量进行操作</p>
     */
    public void volatileMethod() {
        volatileNum++;
    }

}
