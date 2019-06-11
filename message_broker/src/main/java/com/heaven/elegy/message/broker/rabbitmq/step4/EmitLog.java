package com.heaven.elegy.message.broker.rabbitmq.step4;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 以exchange的direct模式进行消息发布
 * <p>direct能在exchange的基础上再对不同的direct进行过滤。用以分配到不同的queue中。同时保证了fanout能同时对不同queue的分发相同消息的能力</p>
 * <p>整体结构与{@link com.heaven.elegy.message.broker.rabbitmq.step3.EmitLog EmitLog}类似。</p>
 * <ol>
 *     <li>修改exchange的模式为direct并添加具体的routingKey</li>
 *     <li>添加对direct的routingKey仿真方法</li>
 * </ol>
 * <b>注: 没有队列可以作为exchange的分配目标时，消息仍然会被抛弃</b>
 * @author li.xiaoxi
 * @date 2019/06/11 15:36
 */
public class EmitLog {

	/**
	 * exchange名
	 */
	private static final String EXCHANGE_NAME = "direct_logs";

	public static void main(String[] args) {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		try(Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();

			// 声明exchange
			channel.exchangeDeclare(
					EXCHANGE_NAME,
					// 这里指定为direct模式
					BuiltinExchangeType.DIRECT
			);

			// 获取级别
			Level level = getLevel();
			// 获取日志
			String log = getLog(level);

			System.out.println(String.join(" ", "发送日志: ", log));

			// 进行消息发布
			channel.basicPublish(
					EXCHANGE_NAME,
					// 使用日志级别对应的字符串作为routingKey
					level.message,
					null,
					log.getBytes(StandardCharsets.UTF_8)
			);


		}catch (Throwable e) {
			e.printStackTrace();
		}

	}

	/**
	 * 根据级别获取日志
	 */
	private static String getLog(Level level) {

		String subMessage;

		switch (level) {
			case DEBUG:
				subMessage = "没啥事:)";
				break;
			case INFO:
				subMessage = "需要观察~";
				break;
			case ERROR:
				subMessage = "报错啦!";
				break;
			default:
				subMessage = "纳尼？没见过的级别？ -> " + level.message;
		}

		return String.join(" ", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), level.message, subMessage);
	}

	/**
	 * 随机获取级别
	 */
	private static Level getLevel() {
		return Level.values()[RandomUtils.nextInt(0, 3)];
	}

	/**
	 * 以枚举形式存在的日志级别
	 */
	public enum Level {
		/**
		 * debug级别
		 */
		DEBUG("debug"),
		/**
		 * info级别
		 */
		INFO("info"),
		/**
		 * error级别
		 */
		ERROR("error");

		String message;

		Level(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return message;
		}}


}
