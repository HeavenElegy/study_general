package com.heaven.elegy.multithreading.example1;

import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 生产者。不断调用{@link MultithreadingExample}的{@link MultithreadingExample#add(int) add(int)}方法
 * @author li.xiaoxi
 * @date 2019/06/12 14:36
 */
public class Producer implements Runnable {

	private MultithreadingExample queueBuffer;

	public Producer(MultithreadingExample queueBuffer) {
		this.queueBuffer = queueBuffer;
		new Thread(this, "Producer").start();
		ThreadPoolExecutor threadPoolExecutor = new ScheduledThreadPoolExecutor(10);
		threadPoolExecutor.execute(new Runnable() {
			@Override
			public void run() {

			}
		});
	}

	@Override
	public void run() {
		while (true) {
			try {
				queueBuffer.add();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new IllegalStateException(e);
			}
		}
	}
}
