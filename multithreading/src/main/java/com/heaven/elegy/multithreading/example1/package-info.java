/**
 * 使用wait与notify，对{@link com.heaven.elegy.multithreading.example1.MultithreadingExample MultithreadingExample}中的方进行限制。使其只能进行get与add方法进行交替调用。阻止同方法的连续调用
 * <p>保护内部变量num在add方法调用时的稳定性</p>
 * @author li.xiaoxi
 * @date 2019/06/12 16:18
 */
package com.heaven.elegy.multithreading.example1;