package com.heaven.elegy.message.broker.rabbitmq.step3;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeoutException;

/**
 * 基于<code>publish/subscribe</code>模式，向消息队列中发送日志消息(其实是经由exchange绑定)
 * <p>大部分与step2的{@link com.heaven.elegy.message.broker.rabbitmq.step2.Send Send} 相同</p>
 * <ol>
 *     <li>在这种使用方法过程中，发送方并没有进行“队列声明”。</li>
 *     <li>因为没有进行“队列声明”。故，如果“成产者”在“消费者”之后启动。那么，在“消费者”启动之前。"生产者"发送的所有消息都将被抛弃。</li>
 *     <li>目前暂时不知道上面的情形是否是因为在fanout模式下所特有的。（按官方所说The messages will be lost if no queue is bound to the exchange yet。可以确定消息被抛弃是必然的与正确的）</li>
 * </ol>
 * @author li.xiaoxi
 * @date 2019/06/11 11:06
 */
public class EmitLog {

	/**
	 * 目标routingKey
	 * <p>用于指定exchange</p>
	 */
	public static final String EXCHANGE_NAME = "logs";
	/**
	 * 要发送的消息
	 */
	public static final String MESSAGE = "Hello World";

	public static void main(String[] args) throws IOException, TimeoutException {

		ConnectionFactory factory = new ConnectionFactory();
		factory.setHost("127.0.0.1");

		try (Connection connection = factory.newConnection()){
			Channel channel = connection.createChannel();

			// 构建日志
			String message = String.join(" - ", DateFormatUtils.format(new Date(), "yyyy-MM-dd HH:mm:ss"), MESSAGE);

			// 声明exchange
			channel.exchangeDeclare(
					EXCHANGE_NAME,
					BuiltinExchangeType.FANOUT
			);

			// 发送消息
			channel.basicPublish(
					// 设置exchange
					EXCHANGE_NAME,
					// 设置routingKey为空字符串。在fanout模式下，这个值将会被忽略
					"",
					// 因为没有经过队列声明的步骤。此参数无效。故填null
					null,
					message.getBytes(StandardCharsets.UTF_8)
			);

			System.out.println("消息发送完成。message:" + message);
		}catch (Throwable e) {
			e.printStackTrace();
		}
	}



}
