package com.heaven.elegy.study.agent.jdk.proxy;

import com.heaven.elegy.study.agent.jdk.proxy.BusinessServiceImpl;
import com.heaven.elegy.study.agent.jdk.proxy.BusinessServiceInterface;
import com.heaven.elegy.study.agent.jdk.proxy.BusinessServiceWithNotInterface;
import com.heaven.elegy.study.agent.jdk.proxy.Core;
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
	 * <p>异常</p>
	 */
	@Test
	public void test02() {
		core.<BusinessServiceWithNotInterface>makeProxyObject(new BusinessServiceWithNotInterface()).hello();
	}

}
