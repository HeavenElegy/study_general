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
	private String JAR;

	public Operation(String JAR) {
		this.JAR = JAR;
	}

	/**
	 * 装载代理
	 */
	public void load() {
		List<VirtualMachineDescriptor> vmList = VirtualMachine.list();
		for (VirtualMachineDescriptor vmd : vmList) {
			load(vmd.id());
		}
	}

	/**
	 * 装载代理
	 * @param pid	进程id
	 */
	public void load(String pid) {
		load(pid, "affect=load");
	}

	/**
	 * 卸载
	 */
	public void unload() {
		List<VirtualMachineDescriptor> vmList = VirtualMachine.list();
		for (VirtualMachineDescriptor vmd : vmList) {
			unload(vmd.id());
		}
	}

	/**
	 * 卸载
	 * @param pid	进程id
	 */
	public void unload(String pid) {
		load(pid, "affect=unload");
	}

	/**
	 * 根据进程id，加载代理
	 * @param pid	进程id
	 * @param params	参数
	 */
	private void load(String pid, String params) {

		try {
			VirtualMachine vm = VirtualMachine.attach(pid);
			vm.loadAgent(JAR, params);
			vm.detach();
		} catch (AttachNotSupportedException | IOException | AgentLoadException | AgentInitializationException e) {
			System.err.println("代理加载失败");
			e.printStackTrace();
		}
	}
}
