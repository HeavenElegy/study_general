package com.heaven.elegy.message.broker.rabbitmq.extend.requeue;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import org.apache.commons.lang3.RandomUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 工作者(消费者)
 * <p>点对点模式。用于实现requeue</p>
 * <p>大部分与step1的{@link com.heaven.elegy.message.broker.rabbitmq.step2.Worker Worker}相同</p>
 * @author li.xiaoxi
 * @date 2019/06/10 16:14
 */
public class Worker {

	/**
	 * 目标队列名
	 * <p>这里修改了队列名。因为之前的“hello”队列已被声明且不可重复声明(虽然可以通过重启Rabbitmq或rabbitmqctl reset命令进行清空)</p>
	 */
	private static final String QUEUE_NAME = "requeue_queue";
	/**
	 * 队列持久化
	 */
	private static final boolean DURABLE = true;
	/**
	 * 关闭自动确认
	 */
	private static final boolean AUTO_ACK = false;
	/**
	 * 分配策略值
	 */
	private static final int PREFETCH_COUNT = 1;

	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		Connection connection = factory.newConnection();
		Channel channel = connection.createChannel();

		// 配置分配策略
		channel.basicQos(PREFETCH_COUNT);

		channel.queueDeclare(
			QUEUE_NAME,
			// 这里进行了持久化相关配置
			DURABLE,
			false,
			false,
			null
		);

		DeliverCallback callback = (consumerTag, delivery) -> {

			String message = new String(delivery.getBody(), StandardCharsets.UTF_8);
			System.out.println("获取消息:" + message);

			if(RandomUtils.nextBoolean()) {
				// 正常确认
				System.out.println("确认消息");
				channel.basicAck(
						delivery.getEnvelope().getDeliveryTag(),
						false
				);
			}else {
				System.err.println("放弃确认并重新入队");
				// 放弃确认
				channel.basicNack(
						delivery.getEnvelope().getDeliveryTag(),
						false,
						true
				);
			}

		};

		channel.basicConsume(
				QUEUE_NAME,
				// 这里进行了自动确认相关配置
				AUTO_ACK,
				callback,
				(consumerTag, message) -> {}
		);

	}


	/**
	 * 使用{@link Thread#sleep(long)}的方式，仿真复杂任务的处理
	 * @param task	中间件中的消息
	 */
	private static void execTask(String task) throws InterruptedException {
		for(char c: task.toCharArray()) {
			if(c == '.') {
				System.out.println(".");
				Thread.sleep(1000);
			}
		}
	}

}
