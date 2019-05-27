package com.heaven.elegy.study.agent.jdk.proxy.agent;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;

/**
 * ClassFileTransformer的实现类。这里不进行任何修改操作。只进行对字节码的输出
 * @author li.xiaoxi
 * @date 2019/05/27 17:38
 */
public class CoreClassFileTransformer implements ClassFileTransformer {

	/**
	 * 输出路径
	 */
	private static final String OUT_PATH = "C:\\Users\\working cat\\Desktop\\temp";

	/**
	 * 被代理的类结果名。
	 * <p>可能需要根据运行时的状态进行修改</p>
	 */
	private static final String TARGET_CLASS = "com/sun/proxy/$Proxy4";

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {


		if(className.equals(TARGET_CLASS)) {
			try {
				writeToFile(className, classfileBuffer);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		return classfileBuffer;
	}


	/**
	 * 写出到class文件
	 */
	private void writeToFile(String className, byte[] bytes) throws IOException {

		FileOutputStream out = new FileOutputStream(new File(OUT_PATH, className.substring(className.lastIndexOf("/")) + ".class"));
		out.write(bytes);
		out.close();
	}


}
