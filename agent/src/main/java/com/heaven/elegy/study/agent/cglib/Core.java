package com.heaven.elegy.study.agent.cglib;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * 基于Cglib的代理对象生成
 * @author li.xiaoxi
 * @date 2019/05/23 16:04
 */
public class Core {

	/**
	 * 代理核心对象
	 * <p>是否应该声明为常量呢？</p>
	 */
	private static final Enhancer ENHANCER = new Enhancer();

	/**
	 * 生成代理对象
	 */
	public <T>T makeProxyObject(final Object object) {
		ENHANCER.setSuperclass(object.getClass());
		ENHANCER.setCallback((MethodInterceptor) (obj, method, args, proxy) -> {
			Object result;
			System.out.println("前~");
			result = method.invoke(object, args);
			System.out.println("后~");
			return result;
		});
		return (T) ENHANCER.create();
	}
}
