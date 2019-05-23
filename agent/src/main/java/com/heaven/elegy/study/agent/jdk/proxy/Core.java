package com.heaven.elegy.study.agent.jdk.proxy;

import java.lang.reflect.Proxy;

/**
 * {@link Proxy Proxy}操作核心
 * @author li.xiaoxi
 * @date 2019/05/22 13:30
 */
public class Core {

	/**
	 * 构建代理对象,同时输出代理后的class到文件
	 * <p>TODO: 2019/5/23 暂时无法输出代理结果类到文件。此种类无法获取</p>
	 */
	public <T>T makeProxyObject(final Object object) {

		Object proxyInstance = Proxy.newProxyInstance(
				getClass().getClassLoader(),
				object.getClass().getInterfaces(),
				(proxy, method, args) -> {
					Object result;
					System.out.println("代理前");
					result = method.invoke(object, args);
					System.out.println("代理后");
					return result;

				});


		return (T) proxyInstance;
	}


}
