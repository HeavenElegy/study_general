package com.heaven.elegy.study.agent.jdk.instrument.premain;

import org.junit.Test;

/**
 * @author li.xiaoxi
 * @date 2019/05/23 14:29
 */
public class Test01 {

	@Test
	public void test01() {
		BusinessService businessService = new BusinessService();
		businessService.hello();
	}
}
