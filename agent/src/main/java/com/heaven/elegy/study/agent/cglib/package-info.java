/**
 * 基于cglib的动态代理技术
 * <p>总结:</p>
 * <ul>
 *     <li>同java的{@link java.lang.reflect.Proxy Proxy}一样，被代理对象的类型已经已经不再是原类型或接口烈性</li>
 *     <li>比java的{@link java.lang.reflect.Proxy Proxy}的优点是，他可以对无接口类进行代理</li>
 *     <li>底层使用了asm技术。而asm是一个字节码操作的技术。代理类是如何生成并加载的呢？</li>
 * </ul>
 * @author li.xiaoxi
 * @date 2019/05/23 16:14
 */
package com.heaven.elegy.study.agent.cglib;