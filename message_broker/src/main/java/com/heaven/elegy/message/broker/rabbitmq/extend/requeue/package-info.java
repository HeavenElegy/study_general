/**
 * <ol>
 *     <li>解决回调函数报错时，使消息重入queue</li>
 *     <li>回调函数主动放弃处理，使消息重入queue</li>
 * </ol>
 *
 * @author li.xiaoxi
 * @date 2019/06/10 18:29
 */
package com.heaven.elegy.message.broker.rabbitmq.extend.requeue;