package com.heaven.elegy.multithreading.example1;

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
