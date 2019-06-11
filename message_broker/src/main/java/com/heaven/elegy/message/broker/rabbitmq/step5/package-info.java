/**
 * 在step4的基础上，修改exchange的模式为topics。用以实现对direct的扩展
 * <p>除表达式以外，并不需要过多需要注意的地方</p>
 * <p>表达式整体结构为<code>word.word.word</code>的方式表现。</p>
 * <p>一些使用方法如下</p>
 * <ul>
 *     <li>这个表达式可以包含任意多个的.与word。既可以表达为.或..或.word.</li>
 *     <li>*代表任意一个word</li>
 *     <li>#代表任意多个word(使用.连接)。当表达式只有一个#时，代表匹配全部的表达式</li>
 *     <li>如a.b.*。匹配a.b.c或a.b.d。并且匹配a.b.，但是不匹配a.b</li>
 *     <li>如a.#。匹配a.b或a.b.c。</li>
 *     <li>当#或*被用于生产者时，被声明的topics将会无法被准确匹配。只能使用#或*以统配形式进行匹配</li>
 *     <li>一些复杂的情况详见{@link com.heaven.elegy.message.broker.rabbitmq.step5.EmitLog.Topics Topics}</li>
 * </ul>
 * @author li.xiaoxi
 * @date 2019/06/11 16:52
 */
package com.heaven.elegy.message.broker.rabbitmq.step5;