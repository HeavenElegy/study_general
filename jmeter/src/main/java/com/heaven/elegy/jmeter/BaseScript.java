package com.heaven.elegy.jmeter;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.jmeter.protocol.http.sampler.HTTPSamplerProxy;
import org.apache.jmeter.threads.JMeterVariables;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author li.xiaoxi
 * @date 2019/06/06 10:23
 */
public abstract class BaseScript {

	/**
	 * 日志
	 */
	protected Logger log = LoggerFactory.getLogger(getClass());

	/**
	 * 采样器实例
	 */
	protected HTTPSamplerProxy sampler = new HTTPSamplerProxy();

	/**
	 * 全局变量
	 */
	protected JMeterVariables vars = new JMeterVariables();

	/**
	 * 此方法的方法体为BeanShell脚本本体
	 */
	abstract void exec();
}
