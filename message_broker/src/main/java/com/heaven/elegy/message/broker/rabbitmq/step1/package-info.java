/**
 * Hello World程序
 * <p>在有多个消费者的情况下(此示例并没有深度配置)，会将消息平均发送给每一个消费者</p>
 * <p>队列声明时必须的，否则会导致发送成功(就客户端而言)。但Rabbitmq服务直接抛弃消息</p>
 * <p>可以使用rabbitmqctl list_queues命令查看当前队列中的数量</p>
 * <p>来自<a>https://www.rabbitmq.com/tutorials/tutorial-one-java.html</a></p>
 * @author li.xiaoxi
 * @date 2019/05/31 17:54
 */
package com.heaven.elegy.message.broker.rabbitmq.step1;