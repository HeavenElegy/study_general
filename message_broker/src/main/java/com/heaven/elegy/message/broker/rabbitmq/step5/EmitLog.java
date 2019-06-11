package com.heaven.elegy.message.broker.rabbitmq.step5;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.RandomUtils;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.nio.charset.StandardCharsets;
import java.util.Date;

/**
 * 以exchange的topics模式进行消息发布
 * <p>相较于direct规则而言，topics可以支持更多的过滤规则。支持以.做为分隔符的以*和#作为通配符的匹配方式</p>
 * <p>整体结构与{@link com.heaven.elegy.message.broker.rabbitmq.step4.EmitLog EmitLog}类似。</p>
 * <ol>
 *     <li>修改exchange的模式为topics并添加具体的routingKey</li>
 *     <li>添加对topics的routingKey仿真方法</li>
 *     <li>修改消息体与routingKey为.word.word的形式</li>
 * </ol>
 * <b>注: 没有队列可以作为exchange的分配目标时，消息仍然会被抛弃</b>
 * <b>如果发送数据时的routingKey中带有#或*会怎么样呢？(表现正常。exchange中的#应该是被作为一个单词使用了，但是已经无法被准确查找了)</b>
 * @author li.xiaoxi
 * @date 2019/06/11 15:36
 */
public class EmitLog {

	/**
	 * exchange名
	 */
	private static final String EXCHANGE_NAME = "topics_logs";

	public static void main(String[] args) {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		try(Connection connection = factory.newConnection()) {

			Channel channel = connection.createChannel();

			// 声明exchange
			channel.exchangeDeclare(
					EXCHANGE_NAME,
					// 这里指定为topics模式
					BuiltinExchangeType.TOPIC
			);

			// 获取topics
			Topics topics = getTopics();
			// 获取message
			String message = getMessage(topics);

			System.out.println(String.join(" " ,"topics:", topics.name(), "message:", message));

			// 进行消息发布
			channel.basicPublish(
					EXCHANGE_NAME,
					// 使用topics作为routingKey
					topics.message,
					null,
					message.getBytes(StandardCharsets.UTF_8)
			);


		}catch (Throwable e) {
			e.printStackTrace();
		}

	}

	private static Topics getTopics() {
		return Topics.values()[RandomUtils.nextInt(0, Topics.values().length)];
	}

	private static String getMessage(Topics topics) {
		return "form the \"" + topics.message + "\"";
	}

	/**
	 * 预先设定的Topics类型(包含了routingKey)
	 */
	public enum Topics {
		/**
		 * 一些假定的Topics
		 */
		A_A_A("a.a.a"),
		A_B_A("a.b.a"),
		A_B_C("a.b.c"),
		X_A_A("x.a.a"),
		X_X_X("x.x.x"),
		/**
		 * 这里向下为topics的一些特殊情况。生产者工作正常，但是对于消费者，需要特殊处理才能捕获
		 */
		EMPTY(""),
		ONE_SPACE(" "),
		ONE_DOT("."),
		TWO_DOT(".."),
		DOT_A_DOT(".a."),
		A_$_A("a.#.a"),
		X_X_$("x.x.#"),
		X_X("x.x.")
		;

		String message;

		Topics(String message) {
			this.message = message;
		}

		@Override
		public String toString() {
			return message;
		}}

}
