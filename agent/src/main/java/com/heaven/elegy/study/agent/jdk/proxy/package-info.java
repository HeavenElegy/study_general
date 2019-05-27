/**
 * 基于<code>java</code>的{@link java.lang.reflect.Proxy Proxy}动态代理技术
 * <p>总结:</p>
 * <ol>
 *     <li>不需要引入第三依赖</li>
 *     <li>对象类名将变更为代理类名</li>
 *     <li>只能对实现了接口的类进行代理。(可能是因为使用了设计模式中的代理模式进行的技术实现的原因)</li>
 *     <li>代理后的类暂时无法导出为字节码</li>
 *     <li>可以获取参数列表、修改参数项、获取返回值、修改返回值、异常捕捉(适用于事务管理)</li>
 * </ol>
 * <p>更新</p>
 * <ol>
 *     <li>添加了基于agent的代理相关类{@link com.heaven.elegy.study.agent.jdk.proxy.agent.Endpoint Endpoint}(需要添加到命令行中进行启动)与{@link com.heaven.elegy.study.agent.jdk.proxy.agent.CoreClassFileTransformer CoreClassFileTransformer}，用于输出代理类字节码。</li>
 * </ol>
 * @author li.xiaoxi
 * @date 2019/05/22 13:23
 */
package com.heaven.elegy.study.agent.jdk.proxy;