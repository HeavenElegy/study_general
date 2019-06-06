package com.heaven.elegy.jmeter

import com.fasterxml.jackson.databind.ObjectMapper
import com.zhongrong.zhonghe.authentication.ProcessHolder
import com.zhongrong.zhonghe.authentication.third.baiDa.BaiDaCipherWrapper
import com.zhongrong.zhonghe.authentication.third.baiDa.ThreeDES
import org.apache.jmeter.config.Arguments
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy
import org.apache.jmeter.testelement.TestElement
import org.apache.jmeter.testelement.property.JMeterProperty
import org.apache.jmeter.testelement.property.PropertyIterator
import org.apache.jmeter.testelement.property.TestElementProperty
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.util.function.BiConsumer

/**
 * 这是一个BeanShell脚本
 *
 * @author li.xiaoxi* @date 2019/06/05 14:08
 */

/**
 * 日志
 */
Logger log = LoggerFactory.getLogger("MyTest");
/**
 * 序列化工具
 */
ObjectMapper om = new ObjectMapper();

/**
 * 采样器实例
 */
HTTPSamplerProxy sampler = new HTTPSamplerProxy();
sampler.addArgument("a", "1");

// -------------------------------------- 可以粘贴的部分 -------------------------------------- \\

try {

    log.info("{}", sampler.getArguments());
    // ------------------- step 1. 拼接openid -------------------
    log.info("开始拼接openid...");
    // TODO 需要写活
    sampler.setPath(sampler.getUrl().toExternalForm() + "?identity=a6f2f4ee025f25a23802734f5357ee40");
    log.info("openid拼接结果:{}", sampler.getUrl());


    // ------------------- step 2. 数据加密 -------------------
    log.info("开始对数据进行加密...");
    // 获取原始json数据
    arguments = sampler.getPropertyAsString("");
    JMeterProperty property = sampler.getArguments().iterator().next();
    json = property.getStringValue();

    log.info("原始json数据:{}", json);

    // 初始化加密器
    instance = ProcessHolder.getInstance(BaiDaCipherWrapper.class);
    instance.init(["012345678901234567890123456789", "01234567"]);

    // 进行加密
    ciphertext = instance.encrypt(json);
    log.info("数据加密结果:{}", ciphertext);
    ciphertext = instance.encrypt(json);
    log.info("数据加密结果:{}", ciphertext);

    // 设置加密后的数据
    Arguments arguments = new Arguments();
    arguments.setProperty("", ciphertext)
    sampler.setArguments(arguments);
    log.info("{}", sampler.getArguments());
    sampler.getPropertyAsString()
}catch (Throwable e) {
    log.error("{}", e);
}
