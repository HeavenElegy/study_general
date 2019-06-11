package com.heaven.elegy.message.broker.rabbitmq.step2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;
import org.apache.commons.lang3.RandomUtils;

/**
 * 向消息队列中发送消息(发送者、生产者)
 * <p>大部分与step1的{@link com.heaven.elegy.message.broker.rabbitmq.step1.Send Send} 相同</p>
 * <ol>
 *     <li>加了代表工作的dot工作功能(模拟)</li>
 *     <li>添加了durable队列持久化功能</li>
 *     <li>添加了{@link MessageProperties#PERSISTENT_TEXT_PLAIN} 消息持久化功能</li>
 * </ol>
 * @author li.xiaoxi
 * @date 2019/06/10 16:06
 */
public class Send {

	/**
	 * 目标队列名
	 */
	public static final String QUEUE_NAME = "task_queue";

	/**
	 * 要发送的消息
	 */
	public static final String MESSAGE = "Hello World";
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

		int r = 10;
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
