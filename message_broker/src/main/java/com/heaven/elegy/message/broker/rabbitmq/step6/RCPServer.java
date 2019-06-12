package com.heaven.elegy.message.broker.rabbitmq.step6;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 对Rabbitmq的消息队列的RCP服务端
 * @author li.xiaoxi
 * @date 2019/06/12 17:02
 */
public class RCPServer {

	private static final String QUEUE_NAME = "rcp_queue";

	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		channel.queueDeclare(
				QUEUE_NAME,
				false,
				false,
				false,
				null
		);

		channel.queuePurge(QUEUE_NAME);

		channel.basicQos(1);

		DeliverCallback callback = (consumerTag, delivery) -> {

			System.out.println("进入Callback");


			AMQP.BasicProperties properties = new AMQP.BasicProperties.Builder()
					// 使用生产者创建的corrId作为处理结果目标队列的消息的corrId
					.correlationId(delivery.getProperties().getCorrelationId())
					.build();

			String result = "";

			try {
				String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
				System.out.println("消息内容: " + message);

				int fib = fib(Integer.parseInt(message));
				System.out.println("斐波那契数列: " + fib);

				result += fib;
			}catch (Throwable e) {
				e.printStackTrace();
			}finally {
				// 推送结果消息
				channel.basicPublish(
						"",
						// 使用生产者的replyTo作为消息处理结果储存的目标队列名
						delivery.getProperties().getReplyTo(),
						// 传递properties。同时传递corrId
						properties,
						result.getBytes(StandardCharsets.UTF_8)
				);

				// 确认消息处理完成
				channel.basicAck(
						delivery.getEnvelope().getDeliveryTag(),
						false
				);
			}


		};

		channel.basicConsume(
				QUEUE_NAME,
				false,
				callback,
				(consumerTag, sig) -> {}
		);




	}




	/**
	 * 计算斐波那契数列的方法
	 * @param step	第几步
	 */
	private static int fib(int step) {
		if(step == 1 || step == 2) {
			return 1;
		}
		return fib(step -1) + fib(step - 2);
	}


}
