package com.heaven.elegy.multithreading.example1;

/**
 * 消费者。不断调用{@link MultithreadingExample}的{@link MultithreadingExample#get() get()}方法
 * @author li.xiaoxi
 * @date 2019/06/12 14:34
 */
public class Customer implements Runnable {

	private MultithreadingExample queueBuffer;

	public Customer(MultithreadingExample queueBuffer) {
		this.queueBuffer = queueBuffer;
		new Thread(this, "Customer").start();
	}

	@Override
	public void run() {
		while (true) {
			try {
				queueBuffer.get();
			} catch (InterruptedException e) {
				e.printStackTrace();
				throw new IllegalStateException(e);
			}
		}
	}
}
