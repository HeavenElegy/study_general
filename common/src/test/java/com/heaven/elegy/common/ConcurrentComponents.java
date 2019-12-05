package com.heaven.elegy.common;

import sun.misc.Unsafe;

import java.lang.reflect.Field;

/**
 * @author li.xiaoxi
 * @since 2019-11-26 14:59
 */
public class ConcurrentComponents {

	public transient volatile int sizeCtl;

	public static final long SIZECTL;


	static final Unsafe U;

	static {
		try {
			Field theUnsafe = Unsafe.class.getDeclaredField("theUnsafe");
			theUnsafe.setAccessible(true);
			U = (Unsafe) theUnsafe.get(null);
			theUnsafe.setAccessible(false);

			Class<ConcurrentComponents> componentsClass = ConcurrentComponents.class;

			SIZECTL = U.objectFieldOffset(componentsClass.getDeclaredField("sizeCtl"));

		} catch (Throwable t) {
			throw new Error(t);
		}

	}

	public int getSizeCtl() {
		return U.getInt(this, SIZECTL);
	}

	public boolean compareAndSwapSizeCtl(int expect, int update) {
		return U.compareAndSwapInt(this, SIZECTL, expect, update);
	}

}
