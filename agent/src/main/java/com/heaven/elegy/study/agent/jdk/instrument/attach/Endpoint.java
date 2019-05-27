package com.heaven.elegy.study.agent.jdk.instrument.attach;

import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * 入口类
 * @author li.xiaoxi
 * @date 2019/05/24 18:46
 */
public class Endpoint {

	/**
	 * 约定入口方法
	 */
	public static void agentmain(String agentArgs, Instrumentation inst) throws UnmodifiableClassException {
		inst.addTransformer(new CoreClassFileTransformer(), true);
		inst.retransformClasses(BusinessService.class);
	}
}
