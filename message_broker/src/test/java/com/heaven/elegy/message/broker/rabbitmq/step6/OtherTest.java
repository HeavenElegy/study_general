package com.heaven.elegy.message.broker.rabbitmq.step6;

import org.junit.Test;

/**
 * 调用{@link OtherTest}类内的方法进行相关测试
 * @author li.xiaoxi
 * @date 2019/06/12 13:14
 */
public class OtherTest {


	@Test
	public void test01() throws InterruptedException {
		Other other = new Other(new Object());
		other.onWait();
	}

	@Test
	public void test02() {
		Other other = new Other(new Object());
		other.onNotify();
	}

	@Test
	public void test03() throws InterruptedException {
		Other other = new Other(new Object());
		other.onWaitBySyn();
	}

	@Test
	public void test04() throws InterruptedException {
		Other other = new Other(new Object());
		new Thread(() -> {
			try {
				other.onWaitBySyn();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}).start();
		new Thread(other::onNotifyBySyn).start();
	}

}
