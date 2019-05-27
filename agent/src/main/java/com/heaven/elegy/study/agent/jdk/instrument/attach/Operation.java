package com.heaven.elegy.study.agent.jdk.instrument.attach;

import com.sun.tools.attach.*;

import java.io.IOException;
import java.util.List;

/**
 * 基于Attach方式的类修改实例
 *
 * @author li.xiaoxi
 * @date 2019/05/27 10:34
 */
public class Operation {

	/**
	 * 代理jar路径(这里是model的jar包)
	 */
	private static final String JAR = "D:\\Coder\\J2EE_Projects\\study\\study_general\\agent\\target\\agent.jar";

	/**
	 * 通过{@link VirtualMachine VirtualMachine} 注册代理jar包
	 */
	public static void execute() {

		List<VirtualMachineDescriptor> vmList = VirtualMachine.list();
		for (VirtualMachineDescriptor vmd : vmList) {
			try {
				VirtualMachine vm = VirtualMachine.attach(vmd.id());
				vm.loadAgent(JAR);
				vm.detach();
			} catch (AttachNotSupportedException | IOException | AgentLoadException | AgentInitializationException e) {
//				e.printStackTrace();
//				System.err.println("代理加载失败");
			}
		}
	}
}
