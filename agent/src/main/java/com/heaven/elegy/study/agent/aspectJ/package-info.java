/**
 * 基于AspectJ技术的代理技术
 * <p><b>AspectJ实际上是切面技术(或静态织入)而不是“代理”技术。</b></p>
 * <p>基于AspectJ的切面切面技术，直接修改编译后的class文件(使用基于Java Agent方式运行时除外)(也是因为这个，其模式也就不再是“代理”的)。</p>
 * <p>总结</p>
 * <ol>
 *     <li>编译前时织入。需要使用aspectjtools与命令行进行织入。(静态代理)</li>
 *     <li>编译后时织入。与上面的差别仅为时基于编译完成后的class进行的。同样需要使用aspectjtools与命令行进行织入。(静态代理)</li>
 *     <li>类加载时织入。通过告知类加载器，使用Java Agent技术进行织入。(动态代理)</li>
 *     <li>Maven自动化织入。此项目中使用的方式，可以自动化完成编译。使用<code>org.codehaus.mojo:aspectj-maven-plugin:1.10</code>
 *     插件自动化完成。内部依旧使用了aspectjtools。(静态代理)</li>
 *     <li>对象的类名不会改变(区别于{@link com.heaven.elegy.study.agent.jdk jdk}的{@link java.lang.reflect.Proxy Proxy}代理)。</li>
 *     <li>可以在编译输出路径直接看到经过切面处理的class文件</li>
 *     <li>可以获取参数列表、修改参数项、获取返回值、修改返回值、异常捕捉(适用于事务管理)</li>
 *     <li>可以操作无接口的类</li>
 *     <li>Spring没有使用AspectJ的相关技术。虽然它也有@AspectJ注解</li>
 * </ol>
 * <p>需要引入如下依赖:</p>
 * <ul>
 *     <li>api: <code>org.aspectj:aspectjrt:1.9.4</code></li>
 *     <li>plugin: <code>org.codehaus.mojo:aspectj-maven-plugin:1.10</code>(本项目直接使用插件进行编译期织入，
 *     所以直接使用插件进行相关处理。具体配置见本项目pom.xml)</li>
 * </ul>
 * @author li.xiaoxi
 * @date 2019/05/23 13:08
 */
package com.heaven.elegy.study.agent.aspectJ;