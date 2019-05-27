/**
 * 基于Attach API的代理技术
 * <p>这种方式<b>可以修改</b>运行中的程序的类。不需要随目标软件一同启动</p>
 * <p>同时，基于AttachAPI，可以实现完整的监控服务(因为底层直接与JVM进行沟通)。这里只是用了很小的一部分功能</p>
 * <p>总结:</p>
 * <ol>
 *     <li>编写操作类。如{@link com.heaven.elegy.study.agent.jdk.instrument.attach.Operation Operation}。进行代理包的加载</li>
 *     <li>编写入口类。{@link com.heaven.elegy.study.agent.jdk.instrument.attach.Endpoint Endpoint}。除入口方法名不同。其他内容与基于agent的{@link com.heaven.elegy.study.agent.jdk.instrument.agent.Endpoint Endpoint}相同。且作用相同</li>
 *     <li>编写转换器。与基于agent的一样</li>
 *     <li>添加manifest描述Agent-Class</li>
 *     <li>直接运行即可看到效果</li>
 * </ol>
 *
 * @author li.xiaoxi
 * @date 2019/05/27 15:25
 */
package com.heaven.elegy.study.agent.jdk.instrument.attach;