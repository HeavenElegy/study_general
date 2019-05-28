package com.heaven.elegy.study.agent.jdk.instrument.agent;


import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.Instrumentation;
import java.lang.instrument.UnmodifiableClassException;

/**
 * Agent入口
 * @author li.xiaoxi
 * @date 2019/05/23 14:12
 */
public class Endpoint {

	/**
	 * 约定(入口)方法。在jvm的某未知阶段进行加载，但在大多数类加载前运行
	 * <p>添加了{@link ClassFileTransformer}的子类{@link CoreClassFileTransformer}实例，用于进行类操作</p>
	 * @param args
	 * @param inst
	 */
	public static void premain(String args, Instrumentation inst) {
		inst.addTransformer(new CoreClassFileTransformer(), true);
	}

}
