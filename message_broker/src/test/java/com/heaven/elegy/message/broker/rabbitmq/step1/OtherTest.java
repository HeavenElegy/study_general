package com.heaven.elegy.message.broker.rabbitmq.step1;

import org.junit.Test;

/**
 * 用于测试{@link Other} 中的相关代码实例
 * @author li.xiaoxi
 * @date 2019/06/10 15:47
 */
public class OtherTest {


	/**
	 * 测试无队列声明的情况下，直接进行push
	 */
	@Test
	public void test01() {
		Other.pushForNotQueueDeclare();
	}



}
