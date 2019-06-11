package com.heaven.elegy.message.broker.rabbitmq.step1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;


/**
 * 用于执行对step1中的一些疑问，以代码的方式进行测试
 * @author li.xiaoxi
 * @date 2019/06/10 15:23
 */
public class Other {



	/**
	 * 在不进行申请队列的情况下直接进行push
	 * <p>结果:</p>
	 * <ol>
	 *     <li>使用命令<code>rabbitmqctl.bat list_queues</code>无法查询到目标队列与消息记录</li>
	 *     <li>基于发送失败的情况。客户端没有返回任何的异常</li>
	 * </ol>
	 */
	public static void pushForNotQueueDeclare() {

		// 在创建连接与渠道后，直接进行消息push。而不是先申请一个queue
		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.01");
		try (Connection connection = factory.newConnection()){
			Channel channel = connection.createChannel();
			channel.basicPublish("", "test01", null, "test01".getBytes("UTF-8"));
		}catch (Throwable e) {
			e.printStackTrace();
			throw new IllegalStateException(e);
		}

	}
}
