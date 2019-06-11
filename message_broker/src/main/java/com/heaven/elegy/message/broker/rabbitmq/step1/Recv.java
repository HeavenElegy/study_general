package com.heaven.elegy.message.broker.rabbitmq.step1;

import com.rabbitmq.client.*;

import java.io.IOException;

/**
 * 队列消费者
 * @author li.xiaoxi
 * @date 2019/05/31 18:24
 */
public class Recv {

	/**
	 * 目标队列名
	 */
	public static final String QUEUE_NAME = "hello";

	public static void main(String[] args) throws Exception {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");
		// 创建连接(这里没有未使用try-with-resource)。因为在会被直接关闭，而本次的方法中，需要线程一直在后台等待进行异步获取数据
		Connection connection = factory.newConnection();
		// 创建通道
		Channel channel = connection.createChannel();

		// 进行队列声明(必须)。否则会导致发送成功但Rabbitmq抛弃请求(按照官网教程中所说，这是为了确保在消费者先于生产者启动时，确保目标队列的存在)
		channel.queueDeclare(
			QUEUE_NAME,
			false,
			false,
			false,
			null
		);

		// 创建一个回调方法。用于异步接收来自消息队列的数据
		DeliverCallback callback = (consumerTag, message) -> System.out.println("consumerTag:" + consumerTag + ", message:" + new String(message.getBody(), "UTF-8"));

		// 绑定消费者
		channel.basicConsume(
			QUEUE_NAME,
			true,
			callback,
			consumerTag -> {}
		);

	}

}
