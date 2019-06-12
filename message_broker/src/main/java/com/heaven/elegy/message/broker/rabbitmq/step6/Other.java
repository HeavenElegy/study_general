package com.heaven.elegy.message.broker.rabbitmq.step6;

/**
 * 主要针对，对顶的教程中使用到的线程同步机制进行相关学习与测试
 * @author li.xiaoxi
 * @date 2019/06/12 13:11
 */
public class Other {

	private final Object look;

	public Other(Object look) {
		this.look = look;
	}

	/**
	 * 直接调用对象的wait方法
	 * @throws InterruptedException 此方法必定抛出此异常
	 */
	void onWait() throws InterruptedException {
		// 此方法不经过同步块，而直接使用将会抛出异常
		look.wait();
	}

	/**
	 * 直接调用对象的notify方法
	 * <p>对象的notify没有声明需要处理的异常。但是在对象未经过同步块的情况下，还是会抛出相同的{@link IllegalMonitorStateException}.
	 * <p>{@link Object#notify()}的注释中已经说明了需要捕获的异常</p>
	 * @throws InterruptedException 此方法必定抛出此异常
	 */
	void onNotify() {
		// 此方法不经过同步块进行直接调用将会抛出异常。(这个方法并没有声明throws任何Exception)
		look.notify();
	}


	/**
	 * 先声明同步块。在进行wait
	 * <p>执行同步块内的wait时。方法执行进入阻塞状态</p>
	 * @throws InterruptedException	此方法将不会抛出此异常
	 */
	void onWaitBySyn() throws InterruptedException {
		synchronized (look) {
			look.wait();
		}
	}

	/**
	 * 先声明同步块。在进行notify
	 */
	void onNotifyBySyn() {
		synchronized (look) {
			look.notify();
		}
	}


}
