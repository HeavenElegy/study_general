package com.heaven.elegy.message.broker.rabbitmq.step3;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 接收日志(消费者)
 * <p>使用exchange的fanout模式进行声明。可以同时启动多个消费者并同时接收到“同一条”来自生产者的消息（一条消息将被多个消费者接收）</p>
 * <p>经由如下步骤进行声明</p>
 * <ol>
 *     <li>...</li>
 *     <li>声明channel</li>
 *     <li>声明exchange</li>
 *     <li>声明一个接收来自exchange消息的队列</li>
 *     <li>绑定exchange与queue</li>
 *     <li>创建回调</li>
 *     <li>...</li>
 * </ol>
 * <p><b>此种模式下。在消费者启动之前，生产者的消息将被抛弃</b></p>
 * @author li.xiaoxi
 * @date 2019/06/11 13:13
 */
public class ReceiveLogs {

	/**
	 * 目标routingKey
	 * <p>用于指定exchange</p>
	 */
	private static final String EXCHANGE_NAME = "logs";

	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		// 声明exchange
		channel.exchangeDeclare(
				EXCHANGE_NAME,
				BuiltinExchangeType.FANOUT
		);

		// 声明队列。使用默认设置，申请一个队列并获取队列名
		String queueName = channel.queueDeclare().getQueue();
		System.out.println("声明队列名: " + queueName);

		// 绑定队列与exchange
		channel.queueBind(
				// 队列名
				queueName,
				// exchange名
				EXCHANGE_NAME,
				// 设置routingKey为空字符串。在fanout模式下，这个值将会被忽略
				""
		);

		System.out.println("初始化完成。等待接收消息...");

		// 构造处理回调
		DeliverCallback callback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("接收到日志: " + message);

		};

		// 添加消费者
		channel.basicConsume(
				queueName,
				true,
				callback,
				(consumerTag, sig) -> {}
		);

	}


}
