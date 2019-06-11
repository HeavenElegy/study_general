package com.heaven.elegy.message.broker.rabbitmq.step5;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * 以exchange的topics模式进行消息接收
 * <p>在exchange的topics模式下。通过topics表达式进行信息获取</p>
 * <p>整体结构与{@link com.heaven.elegy.message.broker.rabbitmq.step4.ReceiveLogs ReceiveLogs}类似。</p>
 * <ol>
 *     <li>修改exchange的模式为topics</li>
 *     <li>并添加具体的routingKey</li>
 * </ol>
 * <p><b>如果是基于IDEA等ide进行运行，需手动配置其启动参数。添加topics表达式。具体参见{@link com.heaven.elegy.message.broker.rabbitmq.step5.EmitLog.Topics Topics}</b></p>
 * @author li.xiaoxi
 * @date 2019/06/11 16:04
 */
public class ReceiveLogs {

	/**
	 * exchange名
	 */
	private static final String EXCHANGE_NAME = "topics_logs";

	public static void main(String[] args) throws IOException, TimeoutException {

		String[] topics = introspectionArgs(args);

		System.out.println(String.join(" ", "接收类型:", Arrays.toString(topics)));

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		// 声明exchange
		channel.exchangeDeclare(
				EXCHANGE_NAME,
				// 使用topics模式
				BuiltinExchangeType.TOPIC
		);

		// 使用默认的方式声明一个queue并获取他的名字
		String queueName = channel.queueDeclare().getQueue();

		// 遍历每一个级别。向channel添加routingKey
		for(String topic: topics) {
			channel.queueBind(
					queueName,
					EXCHANGE_NAME,
					// 添加topic
					topic
			);
		}

		DeliverCallback callback = (consumerTag, delivery) -> {
			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("接收到消息: " + message);
		};

		channel.basicConsume(
				queueName,
				true,
				callback,
				(consumerTag, sig) -> {}
		);


	}

	/**
	 * 检查参数是否符合要求
	 * <p>必须包含最好一个参数</p>
	 */
	private static String[] introspectionArgs(String...args) {

		if(args.length == 0) {
			throw new IllegalStateException("参数个数不能为0");
		}

		return args;
	}
}
