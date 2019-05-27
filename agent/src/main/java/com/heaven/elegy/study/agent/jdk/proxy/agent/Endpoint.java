package com.heaven.elegy.study.agent.jdk.proxy.agent;


import java.lang.instrument.Instrumentation;

/**
 * 入口类。这里用于植入一个用于输出字节码的代理类{@link CoreClassFileTransformer}
 * @author li.xiaoxi
 * @date 2019/05/24 18:46
 */
public class Endpoint {

	/**
	 * 约定入口方法
	 */
	public static void premain(String agentArgs, Instrumentation inst) {
		inst.addTransformer(new CoreClassFileTransformer(), true);
	}
}
