package com.heaven.elegy.multithreading.example1;

/**
 * @author li.xiaoxi
 * @date 2019/06/12 14:43
 */
public class Main {

	public static void main(String[] args) {
		MultithreadingExample queueBuffer = new MultithreadingExample();
		new Producer(queueBuffer);
		new Customer(queueBuffer);
//		new Producer(queueBuffer);
//		new Customer(queueBuffer);
	}

}
