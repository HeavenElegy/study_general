package com.heaven.elegy.study.agent.jdk.instrument.attach;

import org.junit.Test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Spliterator;

/**
 * @author li.xiaoxi
 * @date 2019/05/24 18:51
 */
public class Test01 {

	/**
	 * 用于循环调用固定方法
	 */
	@Test
	public void loop() throws InterruptedException {
		BusinessService businessService = new BusinessService();
		while (true) {
			Thread.sleep(1000);
			businessService.hello();
		}
	}

	/**
	 * 动态修改类方法
	 */
	@Test
	public void load() {
		Operation operation = new Operation("/mnt/d/Coder/J2EE_Projects/study/study_general/agent/target/agent.jar");
		operation.load();
	}

	/**
	 * 还原被修改的类
	 */
	@Test
	public void unload() {
		Operation operation = new Operation("D:\\Coder\\J2EE_Projects\\study\\study_general\\agent\\target\\agent.jar");
		operation.unload();
	}


	@Test
	public void test01() {
		HashMap<String, Integer> map = new HashMap<>();
		map.put("a", 1);
		System.out.println(map);

		map.entrySet().stream().forEach(entry -> entry.setValue(entry.getValue() + 1));

		System.out.println(map);
	}

	@Test
	public void test02() {
		ArrayList<Integer> list = new ArrayList<>();
		for(int i = 0; i < 10; i ++) {
			list.add(i);
		}


		Spliterator<Integer> spliterator1 = list.spliterator();
		Spliterator<Integer> spliterator2 = spliterator1.trySplit();
		spliterator1.forEachRemaining(System.out::println);
		spliterator2.forEachRemaining(System.out::println);

	}


	@Test
	public void test03() {
		Node root = new Node(-1, null);
		int i = 0;
		Node current = root;
		do {
			current.next = new Node(i, null);
			i++;
			System.out.println(i);
		}while ((current = current.next) != null && i < 10);

		Node loHead = null,loTail = null;
		Node hiHead = null,hiTail = null;
		Node next;
		Node e = root;
		do{
			next = e.next;

			if(e.value%2 == 0) {
				if(loTail == null) {
					loHead = e;
				}else {
					loTail.next = e;
				}
				loTail = e;
			}else {
				if(hiTail == null) {
					hiHead = e;
				}else {
					hiTail.next = e;
				}
				hiTail = e;
			}
		}while ((e = next)!=null);

		if(loTail!=null) {
			loTail.next = null;
		}
		if(hiTail!=null) {
			hiTail.next = null;
		}

		System.out.println(loHead);
		System.out.println(hiHead);

	}





	class Node {
		private Integer value;
		private Node next;

		public Node(Integer value, Node next) {
			this.value = value;
			this.next = next;
		}
	}


}
