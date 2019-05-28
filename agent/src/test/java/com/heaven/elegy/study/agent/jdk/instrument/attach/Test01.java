package com.heaven.elegy.study.agent.jdk.instrument.attach;

import org.junit.Test;

/**
 * @author li.xiaoxi
 * @date 2019/05/24 18:51
 */
public class Test01 {

	/**
	 * 用于循环调用固定方法
	 */
	@Test
	public void loop() throws InterruptedException {
		BusinessService businessService = new BusinessService();
		while (true) {
			Thread.sleep(1000);
			businessService.hello();
		}
	}

	/**
	 * 动态修改类方法
	 */
	@Test
	public void load() {
		Operation operation = new Operation("D:\\Coder\\J2EE_Projects\\study\\study_general\\agent\\target\\agent.jar");
		operation.load();
	}

	/**
	 * 还原被修改的类
	 */
	@Test
	public void unload() {
		Operation operation = new Operation("D:\\Coder\\J2EE_Projects\\study\\study_general\\agent\\target\\agent.jar");
		operation.unload();
	}
}
