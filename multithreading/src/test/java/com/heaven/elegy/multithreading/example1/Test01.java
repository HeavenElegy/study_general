package com.heaven.elegy.multithreading.example1;

import org.junit.Test;

/**
 * @author li.xiaoxi
 * @date 2019/06/12 14:42
 */
public class Test01 {

	@Test
	public void test01() {
		try {
			if(1==1) {
				throw new IllegalStateException("e");
			}
		}finally {
			System.out.println("1");
		}
		System.out.println("2");
	}

}
