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

		// 创建连接工厂
		ConnectionFactory factory = new ConnectionFactory();
		// 设置主机(貌似可以再newConnection()时设定)
		factory.setHost("localhost");

		// 以try-with-resource的方式创建连接。
		// 免去在finally中写close方法(这个连接，不会随方法结束而关闭。导致程序无法结束)
		try (Connection connection = factory.newConnection()){
			// 创建通道
			Channel channel = connection.createChannel();

			// 先向消息中间件中申请一个队列
			channel.queueDeclare(
					// 队列名
					QUEUE_NAME,
					// 不进行持久化（消息中间件重启后消失）
					false,
					// TODO 未知
					false,
					// 长期未使用也不进行删除
					false,
					// TODO 不知道有什么用的Map对象
					null
			);

			// 再想消息中间件中发送一个消息(queueDeclare步骤必须要执行)
			channel.basicPublish(
					// TODO 交易？？？？
					"",
					// 目标队列名
					QUEUE_NAME,
					// TODO 不知道有什么用的属性对象
					null,
					// 发送的文本内容(转换为byte数组)
					MESSAGE.getBytes()
			);

			System.out.println("消息发送完成");
		}catch (Exception e) {
			e.printStackTrace();
		}

	}



}
