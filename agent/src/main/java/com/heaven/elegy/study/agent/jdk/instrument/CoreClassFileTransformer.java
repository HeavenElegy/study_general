package com.heaven.elegy.study.agent.jdk.instrument;

import javassist.*;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * 使用java agent机制进行类修改
 * @author li.xiaoxi
 * @date 2019/05/23 13:59
 */
public class CoreClassFileTransformer implements ClassFileTransformer {

	private Transformer transformer = new Transformer();

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		//只处理本包下的类BusinessService
		if(className.equals("com/heaven/elegy/study/agent/jdk/instrument/BusinessService")) {
			// 进行类操作
			return transformer.transform(classfileBuffer);
		}else {
			// 返回源类字节码
			return classfileBuffer;
		}
	}

	/**
	 * 基于javassist的类操作封装。用于操作{@link BusinessService#hello()}方法的字节码
	 */
	public class Transformer {

		/**
		 * 操作方法体
		 * <ul>
		 *     <li>在方法首行添加一行打印</li>
		 *     <li>在方法末行添加一行打印</li>
		 * </ul>
		 */
		public byte[] transform(byte[] in) {
			try {
				ClassPool classPool = ClassPool.getDefault();
				CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(in));
				CtMethod method = ctClass.getDeclaredMethod("hello");
				method.insertBefore("System.out.println(\"这是前面\");");
				method.insertAfter("System.out.println(\"这是前面\");");
				return ctClass.toBytecode();
			} catch (CannotCompileException | NotFoundException | IOException e) {
				e.printStackTrace();
				return in;
			}
		}

	}



}
