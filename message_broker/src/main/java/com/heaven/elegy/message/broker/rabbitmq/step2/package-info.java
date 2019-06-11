/**
 * <p>本包内相对于step1主要扩展了如下几点</p>
 * <ol>
 *     <li>添加假工作任务</li>
 *     <li>对队列持久化的支持</li>
 *     <li>生产者对消息的持久化支持</li>
 *     <li>消费者的异步确认支持</li>
 *     <li>消费者的在异步确认时的分配策略支持</li>
 * </ol>
 * <p>一些注意事项:</p>
 * <ol>
 *     <li>在进行队列声明时，消费者端与生产者端的所有参数必须相同。否则会抛出异常</li>
 *     <li>进行消息持久化时，队列与消息是分开设置的</li>
 *     <li>声明消费者时，prefetchCount应该与acknowledged配合使用。否则会导致消息发送策略有问题。非异步确认的消费者设置prefetchCount会导致消费者在接收到prefetchCount个消息后，停止接收任何消息。(可能是因为确认消息操作失效了)</li>
 * </ol>
 * <p>可以使用rabbitmqctl list_queues命令查看当前队列中的数量</p>
 * @author li.xiaoxi
 * @date 2019/06/10 18:29
 */
package com.heaven.elegy.message.broker.rabbitmq.step2;