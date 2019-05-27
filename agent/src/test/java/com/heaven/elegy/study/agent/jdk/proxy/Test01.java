package com.heaven.elegy.study.agent.jdk.proxy;

import org.junit.Test;

/**
 * @author li.xiaoxi
 * @date 2019/05/22 13:36
 */
public class Test01 {


	private Core core = new Core();
	private static final String OUT_PATH = "C:\\Users\\working cat\\Desktop\\temp";

	/**
	 * 测试基于JDK的动态代理
	 * <p>其被代理类继承了接口</p>
	 * <p>正常</p>
	 */
	@Test
	public void test01() {
		core.<BusinessServiceInterface>makeProxyObject(new BusinessServiceImpl()).hello();
	}

	/**
	 * 测试基于JDK的动态代理
	 * <p>其被代理类没有继承接口</p>
	 * <p>会抛出异常</p>
	 */
	@Test
	public void test02() {
		core.<BusinessServiceWithNotInterface>makeProxyObject(new BusinessServiceWithNotInterface()).hello();
	}

	/**
	 * 尝试使用agent获取类字节码
	 */
	@Test
	public void test03() {
		BusinessServiceInterface proxyObject = core.makeProxyObject(new BusinessServiceImpl());
		// 这里获取代理类完全限定名。用于回填到com.heaven.elegy.study.agent.jdk.proxy.agent.CoreClassFileTransformer.TARGET_CLASS用作过滤
		System.out.println(proxyObject.getClass());

	}
}
