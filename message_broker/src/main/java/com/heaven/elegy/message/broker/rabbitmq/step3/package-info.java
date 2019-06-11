/**
 * <p>实现了一条信息可以被多个消费者接收</p>
 * <p>通过exchange的fanout模式。将每一条消息发送到所有绑定到此exchange的queue中</p>
 * <p>相对于step2。到channel声明之后到回调声明之前。均有较大修改</p>
 * <p>生产者端:</p>
 * <ul>
 *     <li>不再使用queue相关方法</li>
 *     <li>直接声明exchange并设置为fanout模式</li>
 *     <li>发送消息是绑定了exchangeName同时对routingKey与preps进行了置空处理</li>
 *     <li><b>数据不再安全。在消费者启动之前发送的消息将被丢弃</b></li>
 * </ul>
 * <p>消费者端:</p>
 * <ul>
 *     <li>同样直接声明exchange并设置为fanout模式</li>
 *     <li>使用无参数的方法获申请了一个队列实例并获取了队列名(这一步的队列是自动删除的。关闭连接就会消失)</li>
 *	   <li>进行了队列与exchange的绑定</li>
 *	   <li>基于exchange的fanout模式。可以时多个消费者接收到相同的消息</li>
 * </ul>
 * <p>具体的exchange与queue与消费者之间的关系如下(猜测)</p>
 * <ol>
 *     <li>生产者经过exchange绑定。将所有信息发送到exchange中</li>
 *     <li>exchange通过其设定模式(fanout)得知。将此exchange所有收到的消息发送到所有绑定此exchange的queue中。此过程中忽略routingKey</li>
 *     <li>在上述的exchange分配的过程中。如果分配时没有任何可复合要求的queue。则消息被直接抛弃</li>
 *     <li>若存在一个或多个绑定了此exchange的queue，则每个queue都将获得相同的消息</li>
 * </ol>
 * <p>exchange貌似自带了持久化。但是具体持久化策略需要确定</p>
 * <p>{@link com.rabbitmq.client.Channel#basicPublish(java.lang.String, java.lang.String, com.rabbitmq.client.AMQP.BasicProperties, byte[]) Channel.basicPublish}使用方法在step1、step2中与step3中存在明显差别。主要体现在对第二个参数上。前者填入了queueName，但其参数描述为routingKey？</p>
 * <p>可以使用rabbitmqctl list_exchanges命令查看exchange信息</p>
 * <p>可以使用rabbitmqctl list_bindings命令查看绑定信息</p>
 * @author li.xiaoxi
 * @date 2019/06/11 11:05
 */
package com.heaven.elegy.message.broker.rabbitmq.step3;