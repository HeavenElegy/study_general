package com.heaven.elegy.study.agent.cglib;

import org.junit.Test;

/**
 * @author li.xiaoxi
 * @date 2019/05/23 16:10
 */
public class Test01 {

	@Test
	public void test01() {
		BusinessService businessService = new BusinessService();
		Core core = new Core();
		core.<BusinessService>makeProxyObject(businessService).hello();
	}
}
