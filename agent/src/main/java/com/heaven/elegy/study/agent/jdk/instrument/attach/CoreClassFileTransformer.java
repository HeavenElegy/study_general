package com.heaven.elegy.study.agent.jdk.instrument.attach;

import javassist.*;

import java.io.*;
import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.Arrays;
import java.util.Iterator;

/**
 * 使用java Attach机制进行类修改(与premain包下的{@link com.heaven.elegy.study.agent.jdk.instrument.agent.CoreClassFileTransformer})内容大体相同
 * <ol>
 *     <li>添加还原类相关逻辑</li>
 * </ol>
 * @author li.xiaoxi
 * @date 2019/05/23 13:59
 */
public class CoreClassFileTransformer implements ClassFileTransformer {

	/**
	 * 转换器
	 */
	private Transformer transformer;

	private static final String TARGET_CLASS_NAME = "com/heaven/elegy/study/agent/jdk/instrument/attach/BusinessService";

	public CoreClassFileTransformer(String params) {
		this.transformer = new Transformer(params);
	}

	@Override
	public byte[] transform(ClassLoader loader, String className, Class<?> classBeingRedefined, ProtectionDomain protectionDomain, byte[] classfileBuffer) throws IllegalClassFormatException {

		System.out.println("扫描类:" + className);

		//只处理本包下的类BusinessService
		if(className.equals(TARGET_CLASS_NAME)) {
			// 进行类操作
			return transformer.transform(classfileBuffer, TARGET_CLASS_NAME);
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
		 * 标记装载还是还原
		 * <p>load=装载 unload=还原</p>
		 */
		private String params;

		public Transformer(String params) {
			this.params = params;
		}

		/**
		 * 操作方法体
		 * <ul>
		 *     <li>在方法首行添加一行打印</li>
		 * </ul>
		 */
		public byte[] transform(byte[] in, String className) {
			String affect = getParam("affect", params);
			switch (affect) {
				case "load":
					return alter(in, className);
				case "unload":
					return restore(in, className);
				default:
					throw new IllegalStateException("未知的操作符:" + params);
			}
		}

		/**
		 * 修改类
		 */
		private byte[] alter(byte[] classBytes, String className) {
			try {
				// 修改类
				ClassPool classPool = ClassPool.getDefault();
				CtClass ctClass = classPool.makeClass(new ByteArrayInputStream(classBytes));
				CtMethod method = ctClass.getDeclaredMethod("hello");
				method.insertBefore("System.out.println(\"我来过啦~\");");

				// 生成字节码
				byte[] bytes = ctClass.toBytecode();

				// 写出到缓存文件
				File targetFile = new File(makeBackupFilePath(className));
				if(!targetFile.getParentFile().exists()) {
					targetFile.getParentFile().mkdirs();
				}
				System.out.println("缓存文件路径:" + targetFile.getAbsolutePath());
				FileOutputStream out = new FileOutputStream(targetFile);
				out.write(classBytes);
				out.close();

				return bytes;
			} catch (CannotCompileException | NotFoundException | IOException e) {
				throw new IllegalStateException(e);
			}
		}

		/**
		 * 还原类
		 */
		private byte[] restore(byte[] classBytes, String className) {

			try {

				File file = new File(makeBackupFilePath(className));
				if(!file.isFile()) {
					return classBytes;
				}

				FileInputStream in = new FileInputStream(makeBackupFilePath(className));
				ByteArrayOutputStream out = new ByteArrayOutputStream();
				int length;
				byte[] data = new byte[1024];
				while ((length = in.read(data))!=-1) {
					out.write(data, 0, length);
				}
				return out.toByteArray();
			} catch (Exception e) {
				throw new IllegalStateException(e);
			}

		}

		/**
		 * 构造备份文件的文件完全路径
		 */
		private String makeBackupFilePath(String className) {
			return "/backup/" + className + ".class.backup";
		}

		private String getParam(String key, String params) {

			Iterator<String> split = Arrays.asList(params.split("=")).iterator();


			while (split.hasNext()) {
				String next = split.next();
				if(next.equals(key)) {
					if(split.hasNext()) {
						return split.next();
					}else {
						return "";
					}
				}
			}

			return "";
		}



	}

}
