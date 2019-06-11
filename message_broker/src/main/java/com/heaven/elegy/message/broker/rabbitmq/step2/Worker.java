package com.heaven.elegy.message.broker.rabbitmq.step2;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 工作者(消费者)
 * <p>大部分与step1的{@link com.heaven.elegy.message.broker.rabbitmq.step1.Recv Recv}相同</p>
 * <ol>
 *     <li>加了代表工作的dot工作功能(模拟)</li>
 *     <li>添加了durable队列持久化功能</li>
 *     <li>添加了Acknowledge完成确认支持</li>
 *     <li>添加了prefetchCount的分配策略支持</li>
 * </ol>
 * @author li.xiaoxi
 * @date 2019/06/10 16:14
 */
public class Worker {

	/**
	 * 目标队列名
	 * <p>这里修改了队列名。因为之前的“hello”队列已被声明且不可重复声明(虽然可以通过重启Rabbitmq或rabbitmqctl reset命令进行清空)</p>
	 */
	private static final String QUEUE_NAME = "task_queue";
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

			try {
				// 执行任务
				execTask(message);
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new IllegalStateException(e);
			}finally {
				// 在任务执行完成后，进行确认。通知中间件删除此消息
				channel.basicAck(delivery.getEnvelope().getDeliveryTag(), false);
			}

			System.out.println("工作完成!");
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
