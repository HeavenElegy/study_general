package com.heaven.elegy.message.broker.rabbitmq.step4;

import com.rabbitmq.client.*;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.TimeoutException;

/**
 * 以exchange的direct模式进行消息接收
 * <p>在exchange的direct模式下。使用不同的routingKey接收不同类别的消息</p>
 * <p>整体结构与{@link com.heaven.elegy.message.broker.rabbitmq.step3.ReceiveLogs ReceiveLogs}类似。</p>
 * <ol>
 *     <li>修改exchange的模式为direct</li>
 *     <li>并添加具体的routingKey</li>
 * </ol>
 * <p><b>如果是基于IDEA等ide进行运行，需手动配置其启动参数。添加{@link com.heaven.elegy.message.broker.rabbitmq.step4.EmitLog.Level Level}的一个或多个</b></p>
 * @author li.xiaoxi
 * @date 2019/06/11 16:04
 */
public class ReceiveLogs {

	/**
	 * exchange名
	 */
	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] args) throws IOException, TimeoutException {

		EmitLog.Level[] levels = introspectionArgs(args);

		System.out.println(String.join(" ", "接收类型:", Arrays.toString(levels)));

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");
		Connection connection = factory.newConnection();

		Channel channel = connection.createChannel();

		// 声明exchange
		channel.exchangeDeclare(
				EXCHANGE_NAME,
				// 使用direct模式
				BuiltinExchangeType.DIRECT
		);

		// 使用默认的方式声明一个queue并获取他的名字
		String queueName = channel.queueDeclare().getQueue();

		// 遍历每一个级别。向channel添加routingKey
		for(EmitLog.Level level: levels) {
			channel.queueBind(
					queueName,
					EXCHANGE_NAME,
					// 以级别添加routingKey
					level.message
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
	private static EmitLog.Level[] introspectionArgs(String...args) {

		if(args.length == 0) {
			throw new IllegalStateException("参数个数不能为0");
		}

		EmitLog.Level[] result = {};

		for(String l: args) {

			try {
				// 使用字符串获取枚举
				EmitLog.Level level = Enum.valueOf(EmitLog.Level.class, l.toUpperCase());
				// 延长数组
				result = Arrays.copyOf(result, result.length+1);
				// 更新最后一个下标
				result[result.length-1] = level;
			}catch (Throwable e) {
				e.printStackTrace();
				throw new IllegalStateException("错误的级别信息: " + l, e);
			}
		}


		return result;
	}
}
