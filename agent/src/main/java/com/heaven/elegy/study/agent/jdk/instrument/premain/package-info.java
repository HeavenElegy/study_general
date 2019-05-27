/**
 * 基于java的instrument的动态代理技术
 * <p><b>在<b>类加载阶段</b>，通过修改字节码，达到“代理”的功能。</b></p>
 * <p>总结:</p>
 * <ol>
 *     <li>这种使用方法会向java中添加一个“钩子”，每个类被加载时，都会调用这个“钩子”(入口为{@link com.heaven.elegy.study.agent.jdk.instrument.premain.Endpoint Endpoint})。</li>
 *     <li>这里的“钩子”为{@link java.lang.instrument.ClassFileTransformer ClassFileTransformer}的子类{@link com.heaven.elegy.study.agent.jdk.instrument.premain.CoreClassFileTransformer CoreClassFileTransformer}。通过操作类的字节码进行类操作</li>
 *     <li>代理所在包，需要使用maven插件org.apache.maven.plugins:maven-jar-plugin:3.1.1.configuration.archive.manifestEntries.Premain-Class={@link com.heaven.elegy.study.agent.jdk.instrument.premain.Endpoint Endpoint}的方式向MANIFEST.MF文件中添加Premain-Class键</li>
 *     <li>依赖方，需要对代理包进行依赖并在命令行中添加-javaagent:agent.jar(可以是绝对路径。例如在本项目中对自己进行代理)</li>
 *     <li>因为是类加载时进行操作。所以这一步可以获取到原字节码与转换后的字节码</li>
 *     <li>对象类名不会改变(如果不做特殊处理)</li>
 * </ol>
 * <p><b>运行测试用例需要先打包</b></p>
 * @author li.xiaoxi
 * @date 2019/05/23 14:45
 */
package com.heaven.elegy.study.agent.jdk.instrument.premain;