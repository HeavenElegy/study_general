package com.heaven.elegy.multithreading.example1;

/**
 * 一个基于{@link Object#wait()}与{@link Object#notify()}实现的并发自增示例
 * @author li.xiaoxi
 * @date 2019/06/12 14:14
 */
public class MultithreadingExample {

	private int num;
	private boolean hasSet;

	/**
	 * 获取值
	 * <p>字方法使用synchronize修饰。暂时被认为是线程安全的。(有待观察)</p>
	 * <p>同时在内部，hasSet=false时，进行等待</p>
	 */
	synchronized int get() throws InterruptedException {
		System.out.println("get: " + "进入get方法");

		if(!hasSet) {
			// 在未设置值时，进入等待状态。直到被唤醒
			System.out.println("get: " + "未设置值。进行等待");
			wait();
		}

		// 能运行到此处说明num必有值。
		// 这里有两种情况。
		// 1. 在未设置值时进入此方法(hasSet=false)。此时if条件语句主动进入等待状态，而后被put方法唤醒，停止阻塞
		// 2. 在已设置值时直接进入此方法(hasSet=true)。运行到到此位置

		System.out.println("get: " + "获取值-> " + num);

		// 修改状态标识
		hasSet = false;

		// 通知唤醒add方法的等待状态
		notify();

		return num;
	}

	/**
	 * 进行自增
	 * <p>字方法使用synchronize修饰。暂时被认为是线程安全的。(有待观察)</p>
	 * <p>同时在内部，hasSet=true时，进行等待</p>
	 */
	synchronized void add() throws InterruptedException {
		System.out.println("add: " + "进入put方法");

		if(hasSet) {
			// 如果已经设置过值。则进入等待状态。知道被唤醒
			System.out.println("add: " + "已设置值。进行等待");
			wait();
		}

		// 能运行到此处说明hasSet必为false。
		// 这里有两种情况。
		// 1. 在已设置值时进入此方法(hasSet=true)。此时if条件语句主动进入等待状态，而后被get方法唤醒，停止阻塞
		// 2. 在已设置值时直接进入此方法(hasSet=false)。运行到到此位置

		// 进行值设置
		this.num++;
		System.out.println("add: " + "自增结果" + num);

		// 修改状态标识
		this.hasSet = true;

		// 通知唤醒get方法的等待状态
		notify();

	}


}
