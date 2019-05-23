package com.heaven.elegy.study.agent.aspectJ;

import org.junit.Test;

/**
 * @author li.xiaoxi
 * @date 2019/05/23 12:04
 */
public class Test01 {

	@Test
	public void test01() {
		BusinessService service = new BusinessService();
		service.hello();
	}
}
