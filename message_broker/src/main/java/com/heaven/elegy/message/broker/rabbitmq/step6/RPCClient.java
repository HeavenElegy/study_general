package com.heaven.elegy.message.broker.rabbitmq.step6;

import com.rabbitmq.client.*;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.concurrent.TimeoutException;

/**
 * RPC客户端
 * @author li.xiaoxi
 * @date 2019/06/12 18:23
 */
public class RPCClient {

	private static final String QUEUE_NAME = "rcp_queue";


	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		try (Connection connection = factory.newConnection()){

			Channel channel = connection.createChannel();

			// ------------ 作为生产者，构造消息并推送到消费者使用的队列中 ------------ //

			// 申请一个用来储存消费者结果的队列
			String replyQueueName = channel.queueDeclare().getQueue();
			// 构造一个唯一识别符。用来作为corrId
			String corrId = UUID.randomUUID().toString();
			// 构造一个斐波那契数列步数
			String step = Integer.toString(RandomUtils.nextInt(0, 33));

			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					// 设置corrId
					.correlationId(corrId)
					// 设置结果目标
					.replyTo(replyQueueName)
					.build();

			// 推送消息
			channel.basicPublish(
					"",
					QUEUE_NAME,
					properties,
					step.getBytes(StandardCharsets.UTF_8)
			);

			// ------------ 作为消费者，获取指定队列中的消息处理结果 ------------ //

			DeliverCallback callback = (consumerTag, delivery) -> {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				System.out.println("获取消息处理结果: " + message);
			};

			// 添加消费者
			channel.basicConsume(
					// 这里使用reply队列
					replyQueueName,
					callback,
					(consumerTag, sig) -> {}
			);
		}catch (Throwable e) {
			e.printStackTrace();
		}



	}




}
