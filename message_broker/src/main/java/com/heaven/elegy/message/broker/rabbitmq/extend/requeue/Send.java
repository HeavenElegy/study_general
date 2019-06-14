package com.heaven.elegy.message.broker.rabbitmq.extend.requeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.RandomUtils;

/**
 * <p>除队列名以外{@link com.heaven.elegy.message.broker.rabbitmq.step2.Send Send} 相同</p>
 * @author li.xiaoxi
 * @date 2019/06/10 16:06
 */
public class Send {

	/**
	 * 目标队列名
	 */
	private static final String QUEUE_NAME = "requeue_queue";

	/**
	 * 要发送的消息
	 */
	private static final String MESSAGE = "Hello World";
	/**
	 * 队列持久化
	 */
	private static final boolean DURABLE = true;


	/**
	 * 通过main方法启动
	 */
	public static void main(String[] args) {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("localhost");

		// 制作一个任务
		String task = makeTask();

		try (Connection connection = factory.newConnection()){
			Channel channel = connection.createChannel();

			channel.queueDeclare(
					QUEUE_NAME,
					// 这里进行了持久化相关配置
					DURABLE,
					false,
					false,
					null
			);

			// 向消息中间件中发送一个消息
			channel.basicPublish(
					"",
					QUEUE_NAME,
					// 对消息进行持久化
					MessageProperties.PERSISTENT_TEXT_PLAIN,
					task.getBytes()
			);

			System.out.println("消息发送完成。message:" + task);
		}catch (Exception e) {
			e.printStackTrace();
		}

	}


	private static String makeTask() {

		int r = RandomUtils.nextInt(1, 11);
		StringBuilder message = new StringBuilder(MESSAGE);
		message.append(" dot:");
		message.append(r);
		while (r > 0) {
			message.append(".");
			r--;
		}
		return message.toString();
	}



}
