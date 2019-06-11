package com.heaven.elegy.message.broker.rabbitmq.step1;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 向消息队列中发送消息(发送者、生产者)
 * @author li.xiaoxi
 * @date 2019/05/31 17:50
 */
public class Send {

	/**
	 * 目标队列名
	 */
	public static final String QUEUE_NAME = "hello";

	/**
	 * 要发送的消息
	 */
	public static final String MESSAGE = "Hello World";


	/**
	 * 通过main方法启动
	 */
	public static void main(String[] args) {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		// 以try-with-resource的方式创建连接。
		// 免去在finally中写close方法(否则，这个连接不会随方法结束而关闭。导致程序无法结束)
		try (Connection connection = factory.newConnection()){
			// 创建通道
			Channel channel = connection.createChannel();

			// 进行队列声明(必须)。否则会导致发送成功但Rabbitmq抛弃请求
			channel.queueDeclare(
					QUEUE_NAME,
					false,
					false,
					false,
					null
			);

			// 向消息中间件中发送一个消息
			channel.basicPublish(
					"",
					QUEUE_NAME,
					null,
					MESSAGE.getBytes()
			);

			System.out.println("消息发送完成");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}



}
