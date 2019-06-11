/**
 * 基于RabbitMQ的消息队列
 * <p>按照官方<a>https://www.rabbitmq.com/getstarted.html</a>。啃英文文档进行编写。可能会出现描述不准确的现象</p>
 * <ol>
 *     <li>对象序列化问题</li>
 *     <li>集群问题</li>
 *     <li>exchange在fanout、direct、topics模式下的消息抛弃问题</li>
 * </ol>
 * @author li.xiaoxi
 * @date 2019/05/31 17:52
 */
package com.heaven.elegy.message.broker.rabbitmq;