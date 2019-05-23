package com.heaven.elegy.study.agent.jdk.proxy;

/**
 * 业务服务实现
 * @author li.xiaoxi
 * @date 2019/05/22 13:26
 */
public class BusinessServiceImpl implements BusinessServiceInterface {

	@Override
	public void hello() {
		System.out.println("hello~");
	}
}
