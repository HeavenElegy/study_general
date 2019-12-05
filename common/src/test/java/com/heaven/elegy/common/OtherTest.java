package com.heaven.elegy.common;


import org.junit.Test;

import java.lang.reflect.*;
import java.math.BigDecimal;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static java.math.BigDecimal.ROUND_DOWN;
import static java.util.concurrent.TimeUnit.*;

/**
 * @author lixiaoxi
 */
public class OtherTest {

//	public void test01() {
//		Obj a = new Obj();
//		Obj b = new Obj();
//
//		boolean bool = a == b;
//	}


	private class Obj {
	}


	@Test
	public void test02() {
		float f = 1.0F;
		Map<Float, Object> map = new HashMap<>();
		Object val = new Object();
		for (int i = 0; i < 10; i++, f += 1F) {
			int fi = Float.floatToIntBits(f);
			System.out.println("float: " + f + ", binary: " + Integer.toBinaryString(fi) + ", hash: " + Objects.hash(f));
		}
	}

	@Test
	public void test03() {

		Class<MyMap> myMapClass = MyMap.class;
		Type genericSuperclass = myMapClass.getGenericSuperclass();
		if (genericSuperclass instanceof ParameterizedType)
			Arrays.stream(((ParameterizedType) genericSuperclass).getActualTypeArguments())
					.forEach(System.out::println);
		Type[] genericInterfaces = myMapClass.getGenericInterfaces();
		Arrays.stream(genericInterfaces)
				.filter(type -> type instanceof ParameterizedType)
				.map(type -> ((ParameterizedType) type))
				.flatMap(parameterizedType -> Arrays.stream(parameterizedType.getActualTypeArguments()))
				.forEach(System.out::println);

	}

	@Test
	public void test04() throws NoSuchFieldException {
		List<String> list = new ArrayList<>();
		Class<? extends List> listClass = list.getClass();
		TypeVariable<? extends Class<? extends List>>[] typeParameters = listClass.getTypeParameters();
		System.out.println(Arrays.toString(typeParameters));

		Field field = listClass.getDeclaredField("elementData");
		field.setAccessible(true);

		Type genericType = field.getGenericType();
		System.out.println(genericType);


	}


	interface InterfaceA<V> {

	}

	interface InterfaceB<K, V> extends InterfaceA<V> {

	}

	abstract class AbstractA<T> implements InterfaceA<T> {

	}


	class MyMap extends AbstractA<Object> implements InterfaceB<String, Object> {

	}

	@Test
	public void test05() {
		int i = Integer.parseUnsignedInt("10000000000000000000000000000000", 2);
		i = i ^ (i >>> 16);
		System.out.println(Integer.toBinaryString(i >>> 16));
		System.out.println(Integer.toBinaryString(i));


	}

	@Test
	public void test06() {
		int i = Integer.parseInt("10000", 2);
		System.out.println(i + ", " + Integer.toBinaryString(i));
		i |= i >>> 1;
		System.out.println(i + ", " + Integer.toBinaryString(i));
		i |= i >>> 2;
		System.out.println(i + ", " + Integer.toBinaryString(i));
		i |= i >>> 4;
		System.out.println(i + ", " + Integer.toBinaryString(i));
		i |= i >>> 8;
		System.out.println(i + ", " + Integer.toBinaryString(i));
		i |= i >>> 16;
		System.out.println(i + ", " + Integer.toBinaryString(i));
		i++;
		System.out.println(i + ", " + Integer.toBinaryString(i));


	}

	class HashIterator {
		public boolean hasNext() {

			return false;
		}
	}


	@Test
	public void test07() {
		int t = 777;
		int o = 0;
		long current = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			o = t % 2;
		}
		System.out.println(System.currentTimeMillis() - current);

		current = System.currentTimeMillis();
		for (int i = 0; i < 1000000000; i++) {
			o = t & 2;
		}

		System.out.println(System.currentTimeMillis() - current);
	}

	@Test
	public void test01() {
		int a, b, temp, count = 100000000;
		long start, time;

		Random random = new Random();


		while (true) {
			System.out.println("-----------------------------------------------");
			a = random.nextInt();
			b = random.nextInt();

			// 模运算部分
			start = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				temp = a % b;
//				preventOptimization(temp);
			}
			long modulo = System.currentTimeMillis() - start;

			// 与运算部分
			start = System.currentTimeMillis();
			for (int i = 0; i < count; i++) {
				temp = a & b;
//				preventOptimization(temp);
			}
			long and = System.currentTimeMillis() - start;

			// 计算两种运算的比
			BigDecimal bigDecimal = new BigDecimal(and)
					.divide(new BigDecimal(modulo), 6, ROUND_DOWN);
			System.out.println(String.format("modulo: %sms, and: %sms, scale: %s", modulo, and, bigDecimal));
		}


	}

//    private static int preventOptimizationVar = 0; // A

//	/**
//	 * 用于阻止jvm的字节码优化技术生效,优化掉for循环中的代码<br>
//	 *
//	 * @param num
//	 */
//	private static void preventOptimization(int num) {
//        preventOptimizationVar += num; // A
//	}

	@Test
	public void test11() {
		LinkedHashMap<String, Object> map = new LinkedHashMap<>(10, 0.75f, true);
		map.put("a", 1);
		map.put("b", 1);
		map.put("c", 1);
		map.put("a", 1);
		map.put("a", 1);
	}

	@Test
	public void test12() {
		System.out.println(Integer.MAX_VALUE + ", " + Integer.toBinaryString(Integer.MAX_VALUE));
		System.out.println(Integer.MIN_VALUE + ", " + Integer.toBinaryString(Integer.MIN_VALUE));
		System.out.println(Integer.MIN_VALUE + 1 + ", " + Integer.toBinaryString(Integer.MIN_VALUE + 1));
	}

	@Test
	public void test13() {
		System.out.println(String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(1.01F))));
		System.out.println(String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(10.01F))));
		System.out.println(String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(100.01F))));
		System.out.println(String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(1000.01F))));
		System.out.println(String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(10000.01F))));
		System.out.println(String.format("%32s", Integer.toBinaryString(Float.floatToIntBits(100000.01F))));
	}

	@Test
	public void test14() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

		A a = new B();
		a.a();

		C c = new C();
		for (String item : c) {

		}

	}

	class A {
		void a() {
			System.out.println("A.a");
		}
	}

	class B extends A {
		@Override
		void a() {
			System.out.println("B.a");
		}
	}

	class C implements Iterable<String> {

		@Override
		public Iterator<String> iterator() {
			return null;
		}
	}

	interface D {
		boolean equals(Object o);
	}


	@Test
	public void test15() throws InterruptedException {

		Thread t1 = new Thread(() -> {

			while (true) {
				try {
					Thread.sleep(1000);
					System.out.println("hi~");
				} catch (InterruptedException e) {
					throw new IllegalStateException(e);
				}
			}
		});
		t1.start();

		new Thread(() -> {
			try {
				Thread.sleep(3000);
			} catch (InterruptedException e) {
				throw new IllegalStateException(e);
			}
			t1.interrupt();
		}).start();

		t1.join();
	}

	private AtomicInteger atomicInteger = new AtomicInteger(0);

	@Test
	public void test16() throws InterruptedException {
		Thread a = new Thread(() -> {
			while (atomicInteger.get() < 1000)
				System.out.println(String.format("A %s", atomicInteger.incrementAndGet()));
		});
		a.setPriority(Thread.MAX_PRIORITY);
		a.start();

		Thread yield = new Thread(() -> {
			while (atomicInteger.get() < 1000) {
				int i = atomicInteger.get();
				if (i % 10 != 0) {
					System.out.println(String.format("让出片段 by %d", i));
//					Thread.yield();
					continue;
				}
				System.out.println(String.format("B %s", atomicInteger.incrementAndGet()));
			}
		});
		yield.setPriority(Thread.MIN_PRIORITY);
		yield.start();

		a.join();
		yield.join();
	}

	@Test
	public void test17() {
		ConcurrentComponents concurrentComponents = new ConcurrentComponents();
		System.out.println(String.format("sizeCtl: %s, SIZECTL: %s", concurrentComponents.sizeCtl, ConcurrentComponents.SIZECTL));
		System.out.println(String.format("sizeCtl: %s", concurrentComponents.getSizeCtl()));
		concurrentComponents.compareAndSwapSizeCtl(0, -1);
		System.out.println(String.format("sizeCtl: %s", concurrentComponents.getSizeCtl()));
	}

	private int count = 0;

	@Test
	public void test18() throws InterruptedException {


		CountDownLatch start = new CountDownLatch(1);
		CountDownLatch end = new CountDownLatch(3);

		Runnable runnable = () -> {
			try {
				start.countDown();
				start.await();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			for (int j = 0; j < 1000; j++) {
				count += 1;
			}
			end.countDown();
		};

		for (int i = 0; i < 30; i++) {
			new Thread(runnable).start();
		}

		end.await();
		System.out.println(count);

	}


	@Test
	public void test19() {
		ConcurrentHashMap<String, Object> map = new ConcurrentHashMap<>();
		map.put("a", new Object());
	}

	@Test
	public void twoSum1() {
		int[] nums = {1, 2, 3, 4, 5, 6, 7};
		int target = 9;
		for (int i = 0; i < nums.length; i++) {
			for (int j = i + 1; j < nums.length; j++) {
				if (nums[i] + nums[j] == target) {
//					return new int[]{i, j};
				}
			}
		}
//		return null;
	}

	@Test
	public void twoSum2() {
		int[] nums = {1, 2, 3, 7, 4, 7, 7, 8, 7};
		int target = 6;

		Map<Integer, Integer> map = new HashMap<>();

		for (int i = 0; i < nums.length; i++) {
			map.put(nums[i], i);
		}

		for (Map.Entry<Integer, Integer> e : map.entrySet()) {
			int remaining = target - e.getKey();

			if (map.containsKey(remaining) && !e.getValue().equals(map.get(remaining))) {
				System.out.println(e.getValue() + ", " + map.get(remaining));
				return;
//				return new int[]{map.get(num), map.get(remaining)};
			}

		}

//		return null;
	}

	@Test
	public void test20() {
		Integer a = Integer.parseInt("10");
		Integer b = Integer.parseInt("10");
		System.out.println(a.equals(b));
		System.out.println(a.hashCode() == b.hashCode());
	}


	public class ListNodeOld {
		String fullText;
		int val;
		ListNodeOld next;

		public ListNodeOld(int val) {
			this.val = val;
		}

		ListNodeOld(int x, ListNodeOld next) {
			val = x;
			this.next = next;
		}
	}

	@Test
	public void test21() {
		Map<ListNodeOld, ListNodeOld> map = new LinkedHashMap<>();
		map.put(strToListNode("243"), strToListNode("564"));
		map.put(strToListNode("564"), strToListNode("1000000000000000000000000000001"));
		map.put(strToListNode("5"), strToListNode("5"));
		map.put(strToListNode("98"), strToListNode("1"));
		map.put(strToListNode("1"), strToListNode("99"));
		map.put(strToListNode("0"), strToListNode("0"));
		map.put(strToListNode("9"), strToListNode("999"));
		map.put(strToListNode("899"), strToListNode("2"));
		map.put(strToListNode("1"), strToListNode("99999"));

		map.forEach((l1, l2) -> {
			ListNodeOld result = addTwoNumbers2(l1, l2);
			StringBuilder sb = new StringBuilder();
			do {
				sb.append(result.val);
				sb.append(", ");
			} while ((result = result.next) != null);
			System.out.println("[" + l1.fullText + "][" + l2.fullText + "] - [" + sb.toString() + "]");
		});

	}

	public ListNodeOld strToListNode(String str) {
		ListNodeOld head = null, tail = null, e = null;
		String fullText = str;

		do {
			int i = Integer.valueOf(str.substring(0, 1));
			str = str.substring(1);
			e = new ListNodeOld(i);

			if (tail == null) {
				head = e;
			} else {
				tail.next = e;
			}
			tail = e;
		} while (str.length() > 0);

		head.fullText = fullText;
		return head;
	}

	/**
	 * 只能处理long以下的
	 *
	 * @param l1
	 * @param l2
	 * @return
	 */
	public ListNodeOld addTwoNumbers1(ListNodeOld l1, ListNodeOld l2) {

		ArrayDeque<Integer> deque = new ArrayDeque<>();

		long countA = 0;
		long countB = 0;

		ListNodeOld next = l1;

		do {
			deque.push(next.val);
		} while ((next = next.next) != null);

		do {
			countA = countA * 10 + deque.pop();
		} while (!deque.isEmpty());

		next = l2;
		do {
			deque.push(next.val);
		} while ((next = next.next) != null);
		do {
			countB = countB * 10 + deque.pop();
		} while (!deque.isEmpty());

		long count = countA + countB;
		System.out.println(count);

		ListNodeOld e = null, head = null, tail = null;

		do {

			long i = count % 10;
			count /= 10;

			e = new ListNodeOld((int) i);

			if (tail == null) {
				head = e;
			} else {
				tail.next = e;
			}

			tail = e;
		} while (count > 0);


		return head;

	}

	public ListNodeOld addTwoNumbers2(ListNodeOld l1, ListNodeOld l2) {

		ListNodeOld l1head = null, l1tail = null, l2head = null, l2tail = null, e1 = l1, e2 = l2;

		int count;
		boolean carry = false;
		do {

			if (l1tail == null)
				l1head = l1tail = e1;
			else
				l1tail = e1;

			if (l2tail == null)
				l2head = l2tail = e2;
			else
				l2tail = e2;

			// 计算
			count = (l1tail == null ? 0 : l1tail.val) + (l2tail == null ? 0 : l2tail.val);

			// 进位
			if (carry)
				count++;

			// 更新进位
			if ((carry = count >= 10)) {
				// 需要进位

				// 修改count
				count -= 10;
			}

			// 保存值
			if (l1tail != null)
				l1tail.val = count;
			if (l2tail != null)
				l2tail.val = count;

			// 停止后续遍历.放在后面,以保证算数与进位结果.作为最后的进位依据
			if (l2tail == null || l1tail == null)
				break;


		} while ((e1 != null && (e1 = e1.next) != null) | (e2 != null && (e2 = e2.next) != null));

		// 进行扩展进位
		if (carry) {
			if (l1tail != null) {
				ListNodeOld next = l1tail.next;
				ListNodeOld e = next;
				if (next == null) {
					l1tail.next = new ListNodeOld(1);
				} else {
					do {
						if (++e.val >= 10) {
							e.val %= 10;
						} else {
							carry = false;
							break;
						}
						next = e;
					} while ((e = e.next) != null);
					if (carry)
						next.next = new ListNodeOld(1);
				}
			}
			if (l2tail != null) {
				ListNodeOld next = l2tail.next;
				ListNodeOld e = next;
				if (next == null) {
					l2tail.next = new ListNodeOld(1);
				} else {
					do {
						if (++e.val >= 10) {
							e.val %= 10;
						} else {
							carry = false;
							break;
						}
						next = e;
					} while ((e = e.next) != null);
					if (carry)
						next.next = new ListNodeOld(1);
				}

			}
		}

		return l1tail == null ? l2head : l1head;

	}


	@Test
	public void test22() {
//		String s = "qweaasad";
		String s = null;
		StringBuilder stringBuilder = new StringBuilder();
		Random random = new Random();
		for (int i = 0; i < 1000000; i++) {
			stringBuilder.append((char) random.nextInt(Character.MAX_VALUE));
		}
		s = stringBuilder.toString();
		start();
		System.out.println(lengthOfLongestSubstring2(s));
		confirm();
	}

	public int lengthOfLongestSubstring1(String s) {


		char[] chars = s.toCharArray();
		LinkedHashSet<Character> temp = new LinkedHashSet<>();

		int max = 0, temp_max;
		for (int i = 0; i < chars.length; i++) {
			temp.clear();
			char a = chars[i];
			temp.add(a);
			temp_max = 1;
			for (int j = i + 1; j < chars.length; j++) {
				char b = chars[j];
				if (temp.contains(b)) {
					break;
				}
				temp_max++;
				temp.add(b);
			}

			if (max < temp_max)
				max = temp_max;
		}

		return max;

	}

	public int lengthOfLongestSubstring2(String s) {

		Set<Character> set = new HashSet<>();

		int n = s.length();

		int ans = 0, i = 0, j = 0;

		while (i < n && j < n) {

			if (set.contains(s.charAt(j))) {
				set.remove(s.charAt(i++));
			} else {
				set.add(s.charAt(j++));
				ans = Math.max(ans, j - i);
			}

		}

		return ans;

	}

	public int lengthOfLongestSubstring3(String s) {
		if (Objects.isNull(s)) {
			return 0;
		}

		if (s.length() == 1) {
			return s.length();
		}
		char[] arr = s.toCharArray();
		int leftIndex = 0;
		int maxLength = 0;
		int length = arr.length;
		for (int j = 0; j < length; j++) {
			for (int k = leftIndex; k < j; k++) {
				if (arr[k] == arr[j]) {
					maxLength = Math.max(maxLength, j - leftIndex);
					leftIndex = k + 1;
					break;
				}
			}
		}
		return Math.max(maxLength, length - leftIndex);
	}

	@Test
	public void test23() {
		Map<int[], int[]> map = new LinkedHashMap<>();
		map.put(new int[]{1, 2, 3, 100, 101}, new int[]{50, 52, 1001, 1003, 1005});
		map.put(new int[]{1, 2, 3, 100, 101, 102}, new int[]{50, 52, 1001, 1003, 1005, 9999});
		map.put(new int[]{}, new int[]{1});
		map.put(new int[]{}, new int[]{1, 2, 3, 4, 5});
		map.put(new int[]{3, 4}, new int[]{1, 2, 5, 6, 7});
		map.put(new int[]{1, 2}, new int[]{1, 2, 3});
		map.put(new int[]{1, 2, 3, 6, 7, 8}, new int[]{4, 5});
		map.put(new int[]{1, 2, 3, 4, 7, 8, 9, 10}, new int[]{5, 6});
		map.put(new int[]{1}, new int[]{2, 3, 4, 5});
		map.put(new int[]{2, 3, 4, 5}, new int[]{1});
		map.put(new int[]{2}, new int[]{1, 3, 4, 5});
		map.put(new int[]{4}, new int[]{1, 2, 3, 5});
		map.put(new int[]{1, 2, 3, 5}, new int[]{4});

		map.forEach((n1, n2) -> {
			System.out.println("----------------------------------------------------");
			List<Integer> list = new ArrayList<>();
			Arrays.stream(n1).forEach(value -> list.add(value));
			Arrays.stream(n2).forEach(value -> list.add(value));
			Collections.sort(list);
			int midd = list.size() / 2;
			double result;
			if (list.size() % 2 == 0) {
				result = (list.get(midd - 1) + list.get(midd)) / 2F;
			} else {
				result = list.get(midd);
			}

			System.out.println(Arrays.toString(n1) + ", " + Arrays.toString(n2) + " - " + list + " - " + result);

			double medianSortedArrays = findMedianSortedArrays(n1, n2);
			if (medianSortedArrays == result) {
				System.err.flush();
				System.out.println(medianSortedArrays + " - ok");
			} else {
				System.out.flush();
				System.err.flush();
				System.err.println(medianSortedArrays + " - fail");
			}
		});
	}

	public double findMedianSortedArrays(int[] nums1, int[] nums2) {

		// 长度
		int al;
		int bl;

		while ((al = nums1.length) != 0 &&
				(bl = nums2.length) != 0 &&
				al + bl > 4) {


			// 中位数
			// 注意,因为是顺序数组.所以az1 < az2,同理bz1 < bz2
			int az1, az2 = 0;
			int bz1, bz2 = 0;

			// 中位数下标
			int azi1, azi2 = 0;
			int bzi1, bzi2 = 0;

			// 奇偶
			boolean aq, bq;

			// 计算数据a
			if ((aq = al % 2 != 0)) {
				// 奇数
				azi1 = al / 2;

				az1 = nums1[azi1];
			} else {
				// 偶数
				azi1 = al / 2 - 1;
				azi2 = al / 2;

				az1 = nums1[azi1];
				az2 = nums1[azi2];
			}

			// 计算数据b
			if ((bq = bl % 2 != 0)) {
				// 奇数
				bzi1 = bl / 2;

				bz1 = nums2[bzi1];
			} else {
				// 偶数
				bzi1 = bl / 2 - 1;
				bzi2 = bl / 2;

				bz1 = nums2[bzi1];
				bz2 = nums2[bzi2];
			}

			if (aq && bq) {
				// ab都是奇数
				if (az1 == bz1) {
					return az1;
				} else if (az1 < bz1) {
					// a的中位数大于b的中位数,删除a的尾部与b的头部
					nums1 = removeArr(nums1, azi1, nums1.length);
					nums2 = removeArr(nums2, 0, bzi1 + 1);
				} else {
					nums1 = removeArr(nums1, 0, azi1 + 1);
					nums2 = removeArr(nums2, bzi1, nums2.length);
				}
			} else if (aq) {
				// a是奇数 b是偶数
				if (az1 == bz1 || az1 == bz2) {
					return az1;
				} else if (az1 < bz1 && az1 < bz2) {
					// b的两个中间值均大于a的中间值,删除a头部b尾部
					nums1 = removeArr(nums1, azi1, nums1.length);
					nums2 = removeArr(nums2, 0, bzi2 + 1);
				} else if (az1 > bz1 && az1 > bz2) {
					// b的两个中间值均小于a的中间值,删除a尾部b头部
					nums1 = removeArr(nums1, 0, azi1 + 1);
					nums2 = removeArr(nums2, bzi1, nums2.length);
				} else {
					// a的中间值在b的中间值之间,删除a与b的所有非中间值
					nums1 = removeArr(nums1, azi1, azi1 + 1);
					nums2 = removeArr(nums2, bzi1, bzi2 + 1);
				}

				if (nums1.length == 1) {
					nums1 = new int[]{};
				}
			} else if (bq) {
				// a是偶数 b是奇数
				if (az1 == bz1 || az1 == bz2) {
					return az1;
				} else if (bz1 < az1 && bz1 < az2) {
					// a的两个中间值均大于b的中间值,删除b头部a尾部
					// TODO 这里将a的最大中间值删除了
					nums1 = removeArr(nums1, 0, azi2 + 1);
					nums2 = removeArr(nums2, bzi1 + 1, nums2.length);
				} else if (bz1 > az1 && bz1 > az2) {
					// a的两个中间值均小于b的中间值,删除b尾部a头部
					nums1 = removeArr(nums1, azi2 + 1, nums1.length);
					nums2 = removeArr(nums2, 0, bzi1 + 1);
				} else {
					// b的中间值在a的中间值之间,删除a与b的所有非中间值
					nums1 = removeArr(nums1, azi1, azi2 + 1);
					nums2 = removeArr(nums2, bzi1, bzi1 + 1);
				}
			} else {
				// a与b都是偶数
				if (az1 == bz1 && az2 == bz2) {
					return (az1 + az2) / 2F;
				} else if (az2 < bz1) {
					nums1 = removeArr(nums1, bzi2, nums1.length);
					nums2 = removeArr(nums2, 0, bzi1 + 1);
				} else if (az1 > bz2) {
					nums1 = removeArr(nums1, 0, azi2 + 1);
					nums2 = removeArr(nums2, bzi2, nums2.length);
				} else if (az1 < bz2 && az2 > bz1) {
					nums1 = removeArr(nums1, azi1, azi2 + 1);
					nums2 = removeArr(nums2, bzi1, bzi2 + 1);
				}
			}

//			// 更新数组a
//			if (aq) {
//				// 奇数
//				if (az1 < bz1) {
//					// 左侧小于右侧, 删除a头部
//					nums1 = removeArr(nums1, azi1, nums1.length);
//				} else {
//					nums1 = removeArr(nums1, 0, azi1 + 1);
//				}
//			} else {
//				// 偶数,比较a的中间值的最大值与b的中间值的最小值
//				if (az2 < bz1) {
//					// 左侧小于右侧, 删除a头部
//					nums1 = removeArr(nums1, azi1, nums1.length);
//				} else {
//					nums1 = removeArr(nums1, 0, azi2 + 1);
//				}
//			}
//
//			// 更新数组b
//			if (bq) {
//				// 奇数
//				if (az1 < bz1) {
//					// 左侧小于右侧, 删除b尾部
//					nums2 = removeArr(nums2, 0, bzi1 + 1);
//				} else {
//					nums2 = removeArr(nums2, bzi1, nums2.length);
//				}
//			} else {
//				// 偶数,比较a的中间值的最大值与b的中间值的最小值
//				if (az2 < bz1) {
//					// 左侧小于右侧, 删除b尾部
//					nums2 = removeArr(nums2, 0, bzi1 + 1);
//				} else {
//					nums2 = removeArr(nums2, bzi2, nums2.length);
//				}
//			}

			System.out.flush();
			System.err.flush();
			System.out.println(Arrays.toString(nums1) + ", " + Arrays.toString(nums2));


		}

		List<Integer> results = new ArrayList<>();
		Arrays.stream(nums1).forEach(value -> results.add(value));
		Arrays.stream(nums2).forEach(value -> results.add(value));

		Collections.sort(results);

		if (results.size() % 2 == 0) {
			// 偶数
			int i = results.size() / 2;
			return (results.get(i - 1) + results.get(i)) / 2F;
		} else {
			// 奇数
			return results.get(results.size() / 2);
		}

	}


	public int[] removeArr(int[] arr, int from, int to) {

		if (arr.length % 2 == 0) {
			if (arr.length <= 2) {
				return arr;
			}
		} else {
//			if(arr.length <= 3) {
//				return arr;
//			}
		}
		return Arrays.copyOfRange(arr, from, to);
	}

	@Test
	public void test24() {
		List<String> strings = new ArrayList<>();
//		strings.add("asdsaa");
//		strings.add("");
//		strings.add("ccc");
		strings.add("aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa");
		strings.forEach(s -> System.out.println(longestPalindrome(s)));
	}

	public String longestPalindrome(String s) {

		if (s.length() == 0) return s;

		int start = 0, end = 0;

		for (int i = 1; i < s.length(); i++) {

			if (s.charAt(i - 1) == s.charAt(i)) {
				int is, ie;
				for (is = i - 1, ie = i; is >= 0 && ie < s.length() && s.charAt(is) == s.charAt(ie); is--, ie++) {

					if (end - start < ie - is) {
						start = is;
						end = ie;
					}
				}
			}
			if (i >= 2 && s.charAt(i - 2) == s.charAt(i)) {
				int is, ie;
				for (is = i - 2, ie = i; is >= 0 && ie < s.length() && s.charAt(is) == s.charAt(ie); is--, ie++) {

					if (end - start < ie - is) {
						start = is;
						end = ie;
					}
				}
			}


		}
		return s.substring(start, end + 1);
	}

	@Test
	public void test25() {

		Map<String, Integer> map = new LinkedHashMap<>();
//		map.put("LEETCODEISHIRING", 4);
//		map.put("PAYPALISHIRING", 4);
//		map.put("AB", 1);
//		map.put("ABCD", 3);
		map.put("ABCDE", 4);

		map.forEach((s, line) -> System.out.println(convert(s, line)));


	}

	public String convert(String s, int numRows) {
		// N结构数据缓存
		List<List<Character>> list = new ArrayList<>();

		// 下一列的类型
		boolean middleCol = false;
		int
				// 长度条件
				predicate = 0,
				// 当前列大小
				currentColSize = numRows,
				// 列编号
				colNum = 0,
				// 前方总数
				front = 0;
		while (predicate < s.length()) {

			// 创建N结构的列
			List<Character> _list = new ArrayList<>();
			list.add(_list);

			// 添加每一列的内容
			if (middleCol) {
				_list.add(null);
				for (int j = front + currentColSize - 1; j >= front; j--) {
					if (j < s.length()) {
						_list.add(s.charAt(j));
					} else {
						_list.add(null);
					}
				}
				_list.add(null);
			} else {
				for (int j = front; j < front + currentColSize && j < s.length(); j++) {
					_list.add(s.charAt(j));
				}
			}

			// 更新条件
			predicate += currentColSize;
			// 更新列类型
			middleCol = !middleCol;
			// 更新行号
			colNum++;
			// 更新前方总数
			front += currentColSize;
			// 更新当前行大小
			currentColSize = middleCol ? (numRows - (numRows > 1 ? 2 : 1)) : numRows;
		}

		StringBuilder builder = new StringBuilder();
		for (int i = 0; i < numRows; i++) {
			for (List<Character> l : list) {
				if (l.size() > i) {
					Character character = l.get(i);
					if (character == null) {
						continue;
					} else {
						builder.append((char) character);
					}
				}
			}
		}

		return builder.toString();
	}

	@Test
	public void test26() {
		List<Integer> list = new ArrayList<>();
		list.add(123);
		list.add(-123);
		list.add(1534236469);
		list.add(-2147483412);
		list.add(1463847412);
		list.forEach(i -> System.out.println(reverse(i)));
	}

	public int reverse(int x) {

		if (x == 0) return x;

		boolean ab = x < 0;
//		x = Math.abs(x);
		int result = 0;

		do {
			int least = x % 10;

			if (ab) {
				// 负数
				for (int i = 0, j = 0; i < 10; i++) {
					if ((j = j + result) > 0) {
						return 0;
					}
				}
			} else {
				// 正数
				for (int i = 0, j = Integer.MAX_VALUE; i < 10; i++) {
					if ((j = j - result) < 0) {
						return 0;
					}
				}
			}

			result = least + result * 10;
			x = x / 10;
		} while (x != 0);

//		if (ab)
//			return result * -1;
		return result;
	}

	@Test
	public void test27() {
		List<String> list = new ArrayList<>();
		list.add("42");
		list.add("      -42");
		list.add("4193 with words");
		list.add("words and 987");
		list.add("-91283472332");
		list.add("2147483648");
		list.add("20000000000000000000");
		list.add("  0000000000012345678");
		list.add("-000000000000001");
		list.add("+-2");
		list.add("   +0 123");
		list.add("0-1");
		list.forEach(i -> System.out.println(i + ", " + myAtoi(i)));

	}

	public int myAtoi(String str) {

		char[] max = {'2', '1', '4', '7', '4', '8', '3', '6', '4', '7'};
		char[] min = {'2', '1', '4', '7', '4', '8', '3', '6', '4', '8'};

		int startIndex = -1;

		// 符号位
		int signal = 0;

		// 寻找开始下标与符号位
		for (int i = 0; i < str.length(); i++) {

			char c = str.charAt(i);

			// 前置判断
			if (signal != 0) {
				if (c == ' ' || c == '-' || c == '+')
					return 0;
			}

			if (c == '-') {
				if (signal != 0)
					// 重复定义,直接返回
					return 0;
				signal = -1;
//				startIndex = ++i;
//				break;
			}

			if (c == '+') {
				if (signal != 0)
					// 重复定义,直接返回
					return 0;
				signal = 1;
//				startIndex = ++i;
//				break;
			}

			// 判断开始下标
			if (signal != 0) {
				if (c >= '1' && c <= '9') {
					startIndex = i;
					break;
				}
			} else {
				if (c >= '0' && c <= '9') {
					if (c == '0' && str.length() > i + 1 && str.charAt(i + 1) == '0')
						continue;
					startIndex = i;
					signal = 1;
					break;
				}
			}

			if (c != ' ' && c != '0' && c != '-' && c != '+') {
				return 0;
			}
		}

		// 没有找到开始下标
		if (startIndex == -1) {
			return 0;
		}

		// 寻找结束位置
		int endIndex = startIndex;

		for (int i = startIndex; i < str.length(); i++) {
			char c = str.charAt(i);
			if (c < '0' || c > '9') {
				endIndex = i;
				break;
			}
			endIndex++;
		}

		// 截取目标
		char[] target = str.substring(startIndex, endIndex).toCharArray();

		// 比较长度,做直接处理
		if (target.length < max.length) {
			// 比最大值位数少1位,可以直接转换
			return myAtoiBub1(target, signal);
		} else if (target.length > max.length) {
			if (signal == 1) {
				return Integer.MAX_VALUE;
			} else {
				return Integer.MIN_VALUE;
			}
		}

		// 进行按位比较,检查是否可以转换
		char[] template;
		if (signal == 1) {
			// 正数
			template = max;
		} else {
			// 负数
			template = min;
		}

		// 进行两个数组的比较,验证上下限是否超标
		int surplus = 0;
		for (int i = template.length - 1; i >= 0; i--) {

			char a = template[i];
			char b = target[i];

			if (surplus != 0) {
				// 存在进位
				int intR = (b - '0') + 1 - (a - '0');
				if (intR <= 0) {
					surplus = 0;
				}
			} else if (a < b) {
				surplus = 1;
			}
		}

		if (surplus != 0) {
			// 存在超标值
			if (signal == 1)
				return Integer.MAX_VALUE;
			else
				return Integer.MIN_VALUE;
		} else {
			// 获取转换结果
			return myAtoiBub1(target, signal);
		}
	}

	public int myAtoiBub1(char[] chars, int signal) {

		int result = 0;

		for (int i = 0; i < chars.length; i++) {
			result = result * 10 + chars[i] - '0';
		}

		return result * signal;
	}

	@Test
	public void test28() {
		List<Integer> integers = new ArrayList<>();
//		integers.add(123321);
//		integers.add(12321);
//		integers.add(12);
//		integers.add(22);
//		integers.add(121);
//		integers.add(1221);
//		integers.add(12321);
//		integers.add(1233421);
//		integers.add(100);
//		integers.add(500);
//		integers.add(131000);
//		integers.add(88888);
//		integers.add(9999);
//		integers.add(2222222);
		integers.add(21120);
		integers.forEach(i -> System.out.println(i + "\t:" + isPalindrome2(i)));
	}

	public boolean isPalindrome1(int x) {

		if (x < 0) {
			return false;
		}

		if (x == 0) {
			return true;
		}
		if (x / 10 == 0) {
			return true;
		}

		int[] arr = {};


		while (x > 0) {
			// 更新各种数据
			arr = Arrays.copyOf(arr, arr.length + 1);
			arr[arr.length - 1] = x % 10;
			x = x / 10;

			// 进行回文字比较
			int tempX = x;

			if (arr.length == 1) {
				// 右侧只有一个缓存,单独处理.比较左侧两个字段
				if (tempX / 10 == 0) {
					return tempX == arr[0];
				} else {
					continue;
				}
			}

			boolean first = true;
			for (int i = arr.length - 1; i >= 0; i--) {

				if (arr[i] != tempX % 10) {
					if (first) {
						// 首次匹配失败可以跳过下一个循环 如12321
						first = false;
						continue;
					}
					break;
				} else {
					if (first) {
						// 用于处理88888的情况.尽量扩展i
						// 添加对arr的长度约束,只针对奇数数组生效
						if (arr.length % 2 != 0 && arr[i - 1] == tempX % 10) {
							first = false;
							continue;
						}
					}
					first = false;
				}


				tempX /= 10;

				if (tempX == 0) {
					if (i == 0)
						return true;
					else
						return false;
				}
			}


		}
		return false;
	}

	public boolean isPalindrome2(int x) {

		if (x < 0) return false;

		if (x == 0 || x / 10 == 0) return true;

		int[] arr = {};

		while (x > 0) {

			// 获取个位数
			int st1 = x % 10;

			// 更新输入值
			x /= 10;

			// 更新arr
			int[] newArr = new int[arr.length + 1];
			newArr[0] = st1;
			System.arraycopy(arr, 0, newArr, 1, arr.length);
			arr = newArr;

			// 检查碰撞方式
			boolean once = arr.length > 1 && x % 10 == arr[1];
			boolean twice = x % 10 == arr[0];

			int onceX = x, twiceX = x;

			if (once) {
				int i;
				for (i = 1; i < arr.length; i++) {
					if (onceX != 0 && onceX % 10 == arr[i]) {
						onceX /= 10;
					} else {
						break;
					}
				}
				if (i == arr.length && onceX == 0) {
					return true;
				}
			}

			if (twice) {
				int i;
				for (i = 0; i < arr.length; i++) {
					if (twiceX != 0 && twiceX % 10 == arr[i]) {
						twiceX /= 10;
					} else {
						break;
					}
				}
				if (i == arr.length && twiceX == 0) {
					return true;
				}
			}

		}
		return false;
	}

	@Test
	public void test29() {
		class Pair {
			String str;
			String pat;

			public Pair(String str, String pat) {
				this.str = str;
				this.pat = pat;
			}
		}
		List<Pair> list = new ArrayList<>();
		list.add(new Pair("aa", "aa"));
		list.add(new Pair("aa", "a"));
		list.add(new Pair("aa", "a*"));
		list.add(new Pair("ab", ".*"));
		list.add(new Pair("aab", "c*a*b"));
		list.add(new Pair("mississippi", "mis*is*p*."));
		list.add(new Pair("ab", ".*c"));
		list.add(new Pair("aaa", "aaaa"));
		list.add(new Pair("aa", "a*a"));
		list.add(new Pair("ab", ".*"));

		list.forEach(p -> System.out.println(String.format("str: %s, pat: %s, result:%s", p.str, p.pat, isMatch(p.str, p.pat))));
	}

	public boolean isMatch(String strs, String pats) {
		if (strs.length() == 0 && pats.length() == 0) return true;

		boolean currentMatch = (strs.length() > 0 && pats.length() > 0) && (strs.charAt(0) == pats.charAt(0) || pats.charAt(0) == '.');

		if (pats.length() > 1 && pats.charAt(1) == '*') {
			// *通配符
			return isMatch(strs, pats.substring(2)) ||
					(currentMatch && isMatch(strs.length() > 1 ? strs.substring(1) : "", pats));
		} else {
			return currentMatch && isMatch(strs.length() > 1 ? strs.substring(1) : "", pats.length() > 1 ? pats.substring(1) : "");
		}
	}

	@Test
	public void test30() {
		List<int[]> list = new ArrayList<>();
		list.add(new int[]{1, 8, 6, 2, 5, 4, 8, 3, 7});
		list.forEach(ints -> System.out.println(String.format("arr: %s, %s", Arrays.toString(ints), maxArea2(ints))));
	}

	public int maxArea1(int[] height) {

		int result = 0;
		for (int i = 0; i < height.length; i++) {
			for (int j = i; j < height.length; j++) {
				int _result = (j - i) * Math.min(height[i], height[j]);
				if (_result > result) {
					result = _result;
				}
			}
		}

		return result;
	}

	public int maxArea2(int[] height) {

		int result = 0;

		int left = 0, right = height.length - 1;

		int temp;

		while (left < right) {

			temp = (right - left) * Math.min(height[left], height[right]);

			if (temp > result) {
				result = temp;
			}

			if (height[left] < height[right]) {
				left++;
			} else {
				right--;
			}
		}


		return result;
	}

	@Test
	public void test31() {
		class Problem {
			int input;
			String answer;

			public Problem(int input, String answer) {
				this.input = input;
				this.answer = answer;
			}
		}


		List<Problem> list = new ArrayList<>();
		list.add(new Problem(3, "III"));
		list.add(new Problem(4, "IV"));
		list.add(new Problem(9, "IX"));
		list.add(new Problem(58, "LVIII"));
		list.add(new Problem(1994, "MCMXCIV"));
		list.add(new Problem(99, "-"));

		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.input, problem.answer, intToRoman(problem.input))));

	}

	public String intToRoman(int num) {

		StringBuilder result = new StringBuilder();

		String[] full = {"I", "X", "C", "M"};
		String[] half = {"V", "L", "D"};

		int power = 0;


		while (num > 0) {
			StringBuilder internal = new StringBuilder();
			int a = num % 10;
			num /= 10;

			if (a == 4) {
				internal.append(full[power])
						.append(half[power]);
			} else if (a == 9) {
				internal.append(full[power])
						.append(full[power + 1]);
			} else {
				if (a >= 5) {
					internal.append(half[power]);
					a %= 5;
				}

				for (int i = a; i > 0; i--) {
					internal.append(full[power]);
				}
			}

			power++;
			result.insert(0, internal);
		}

		return result.toString();
	}

	@Test
	public void test32() {
		class Problem {
			int answer;
			String input;

			public Problem(int answer, String input) {
				this.answer = answer;
				this.input = input;
			}
		}


		List<Problem> list = new ArrayList<>();
		list.add(new Problem(3, "III"));
		list.add(new Problem(4, "IV"));
		list.add(new Problem(9, "IX"));
		list.add(new Problem(58, "LVIII"));
		list.add(new Problem(1994, "MCMXCIV"));

		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.input, problem.answer, romanToInt(problem.input))));
	}

	public int romanToInt(String s) {
		int result = 0;

		int[] nums = {1, 5, 10, 50, 100, 500, 1000};
		char[] roma = {'I', 'V', 'X', 'L', 'C', 'D', 'M'};

		int lastIndex = -1;
		char lastChar = (char) -1;

		for (int i = 0; s.length() > 0; ) {

			char c = s.charAt(s.length() - 1);

			char r = roma[i];

			if (c == r) {
				result += nums[i];
				s = s.substring(0, s.length() - 1);
				continue;
			} else if (lastChar == c) {
				result -= nums[lastIndex];
				s = s.substring(0, s.length() - 1);
				continue;
			} else {
				i++;
			}

			if (i % 2 != 0) {
				lastChar = roma[i - 1];
				lastIndex = i - 1;
			}

		}

		return result;
	}

	@Test
	public void test33() {
		class Problem {
			String[] problem;
			String answer;

			public Problem(String[] problem, String answer) {
				this.problem = problem;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
//		list.add(new Problem(new String[]{"flower","flow","flight"}, "fl"));
//		list.add(new Problem(new String[]{}, "-"));
		list.add(new Problem(new String[]{"babb", "caa"}, "-"));

		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", Arrays.toString(problem.problem), problem.answer, longestCommonPrefix(problem.problem))));

	}

	public String longestCommonPrefix(String[] strs) {
		if (strs.length == 0) {
			return "";
		}
		StringBuilder stringBuilder = new StringBuilder();
		for (int i = 0; ; i++) {
			char temp;
			if (strs[0].length() > i) {
				temp = strs[0].charAt(i);
			} else {
				return stringBuilder.toString();
			}
			for (int j = 1; j < strs.length; j++) {

				if (strs[j].length() <= i || temp != strs[j].charAt(i)) {
					return stringBuilder.toString();
				}
			}
			stringBuilder.append(temp);
		}
	}


	@Test
	public void test34() {
		class Problem {
			int[] problem;
			int[][] answer;

			public Problem(int[] problem, int[][] answer) {
				this.problem = problem;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem(new int[]{-1, 0, 1, 2, -1, -4}, new int[][]{new int[]{-1, 0, 1}, new int[]{-1, -1, 2}}));
		list.add(new Problem(new int[]{0, 0, 0, 0}, new int[][]{new int[]{0, 0, 0}}));
		list.add(new Problem(new int[]{-2, 0, 1, 1, 2}, null));
		list.add(new Problem(new int[]{1, -1, -1, 0}, null));
		list.add(new Problem(new int[]{82597, -9243, 62390, 83030, -97960, -26521, -61011, 83390, -38677, 12333, 75987, 46091, 83794, 19355, -71037, -6242, -28801, 324, 1202, -90885, -2989, -95597, -34333, 35528, 5680, 89093, -90606, 50360, -29393, -27012, 53313, 65213, 99818, -82405, -41661, -3333, -51952, 72135, -1523, 26377, 74685, 96992, 92263, 15929, 5467, -99555, -43348, -41689, -60383, -3990, 32165, 65265, -72973, -58372, 12741, -48568, -46596, 72419, -1859, 34153, 62937, 81310, -61823, -96770, -54944, 8845, -91184, 24208, -29078, 31495, 65258, 14198, 85395, 70506, -40908, 56740, -12228, -40072, 32429, 93001, 68445, -73927, 25731, -91859, -24150, 10093, -60271, -81683, -18126, 51055, 48189, -6468, 25057, 81194, -58628, 74042, 66158, -14452, -49851, -43667, 11092, 39189, -17025, -79173, 13606, 83172, 92647, -59741, 19343, -26644, -57607, 82908, -20655, 1637, 80060, 98994, 39331, -31274, -61523, 91225, -72953, 13211, -75116, -98421, -41571, -69074, 99587, 39345, 42151, -2460, 98236, 15690, -52507, -95803, -48935, -46492, -45606, -79254, -99851, 52533, 73486, 39948, -7240, 71815, -585, -96252, 90990, -93815, 93340, -71848, 58733, -14859, -83082, -75794, -82082, -24871, -15206, 91207, -56469, -93618, 67131, -8682, 75719, 87429, -98757, -7535, -24890, -94160, 85003, 33928, 75538, 97456, -66424, -60074, -8527, -28697, -22308, 2246, -70134, -82319, -10184, 87081, -34949, -28645, -47352, -83966, -60418, -15293, -53067, -25921, 55172, 75064, 95859, 48049, 34311, -86931, -38586, 33686, -36714, 96922, 76713, -22165, -80585, -34503, -44516, 39217, -28457, 47227, -94036, 43457, 24626, -87359, 26898, -70819, 30528, -32397, -69486, 84912, -1187, -98986, -32958, 4280, -79129, -65604, 9344, 58964, 50584, 71128, -55480, 24986, 15086, -62360, -42977, -49482, -77256, -36895, -74818, 20, 3063, -49426, 28152, -97329, 6086, 86035, -88743, 35241, 44249, 19927, -10660, 89404, 24179, -26621, -6511, 57745, -28750, 96340, -97160, -97822, -49979, 52307, 79462, 94273, -24808, 77104, 9255, -83057, 77655, 21361, 55956, -9096, 48599, -40490, -55107, 2689, 29608, 20497, 66834, -34678, 23553, -81400, -66630, -96321, -34499, -12957, -20564, 25610, -4322, -58462, 20801, 53700, 71527, 24669, -54534, 57879, -3221, 33636, 3900, 97832, -27688, -98715, 5992, 24520, -55401, -57613, -69926, 57377, -77610, 20123, 52174, 860, 60429, -91994, -62403, -6218, -90610, -37263, -15052, 62069, -96465, 44254, 89892, -3406, 19121, -41842, -87783, -64125, -56120, 73904, -22797, -58118, -4866, 5356, 75318, 46119, 21276, -19246, -9241, -97425, 57333, -15802, 93149, 25689, -5532, 95716, 39209, -87672, -29470, -16324, -15331, 27632, -39454, 56530, -16000, 29853, 46475, 78242, -46602, 83192, -73440, -15816, 50964, -36601, 89758, 38375, -40007, -36675, -94030, 67576, 46811, -64919, 45595, 76530, 40398, 35845, 41791, 67697, -30439, -82944, 63115, 33447, -36046, -50122, -34789, 43003, -78947, -38763, -89210, 32756, -20389, -31358, -90526, -81607, 88741, 86643, 98422, 47389, -75189, 13091, 95993, -15501, 94260, -25584, -1483, -67261, -70753, 25160, 89614, -90620, -48542, 83889, -12388, -9642, -37043, -67663, 28794, -8801, 13621, 12241, 55379, 84290, 21692, -95906, -85617, -17341, -63767, 80183, -4942, -51478, 30997, -13658, 8838, 17452, -82869, -39897, 68449, 31964, 98158, -49489, 62283, -62209, -92792, -59342, 55146, -38533, 20496, 62667, 62593, 36095, -12470, 5453, -50451, 74716, -17902, 3302, -16760, -71642, -34819, 96459, -72860, 21638, 47342, -69897, -40180, 44466, 76496, 84659, 13848, -91600, -90887, -63742, -2156, -84981, -99280, 94326, -33854, 92029, -50811, 98711, -36459, -75555, 79110, -88164, -97397, -84217, 97457, 64387, 30513, -53190, -83215, 252, 2344, -27177, -92945, -89010, 82662, -11670, 86069, 53417, 42702, 97082, 3695, -14530, -46334, 17910, 77999, 28009, -12374, 15498, -46941, 97088, -35030, 95040, 92095, -59469, -24761, 46491, 67357, -66658, 37446, -65130, -50416, 99197, 30925, 27308, 54122, -44719, 12582, -99525, -38446, -69050, -22352, 94757, -56062, 33684, -40199, -46399, 96842, -50881, -22380, -65021, 40582, 53623, -76034, 77018, -97074, -84838, -22953, -74205, 79715, -33920, -35794, -91369, 73421, -82492, 63680, -14915, -33295, 37145, 76852, -69442, 60125, -74166, 74308, -1900, -30195, -16267, -60781, -27760, 5852, 38917, 25742, -3765, 49097, -63541, 98612, -92865, -30248, 9612, -8798, 53262, 95781, -42278, -36529, 7252, -27394, -5021, 59178, 80934, -48480, -75131, -54439, -19145, -48140, 98457, -6601, -51616, -89730, 78028, 32083, -48904, 16822, -81153, -8832, 48720, -80728, -45133, -86647, -4259, -40453, 2590, 28613, 50523, -4105, -27790, -74579, -17223, 63721, 33489, -47921, 97628, -97691, -14782, -65644, 18008, -93651, -71266, 80990, -76732, -47104, 35368, 28632, 59818, -86269, -89753, 34557, -92230, -5933, -3487, -73557, -13174, -43981, -43630, -55171, 30254, -83710, -99583, -13500, 71787, 5017, -25117, -78586, 86941, -3251, -23867, -36315, 75973, 86272, -45575, 77462, -98836, -10859, 70168, -32971, -38739, -12761, 93410, 14014, -30706, -77356, -85965, -62316, 63918, -59914, -64088, 1591, -10957, 38004, 15129, -83602, -51791, 34381, -89382, -26056, 8942, 5465, 71458, -73805, -87445, -19921, -80784, 69150, -34168, 28301, -68955, 18041, 6059, 82342, 9947, 39795, 44047, -57313, 48569, 81936, -2863, -80932, 32976, -86454, -84207, 33033, 32867, 9104, -16580, -25727, 80157, -70169, 53741, 86522, 84651, 68480, 84018, 61932, 7332, -61322, -69663, 76370, 41206, 12326, -34689, 17016, 82975, -23386, 39417, 72793, 44774, -96259, 3213, 79952, 29265, -61492, -49337, 14162, 65886, 3342, -41622, -62659, -90402, -24751, 88511, 54739, -21383, -40161, -96610, -24944, -602, -76842, -21856, 69964, 43994, -15121, -85530, 12718, 13170, -13547, 69222, 62417, -75305, -81446, -38786, -52075, -23110, 97681, -82800, -53178, 11474, 35857, 94197, -58148, -23689, 32506, 92154, -64536, -73930, -77138, 97446, -83459, 70963, 22452, 68472, -3728, -25059, -49405, 95129, -6167, 12808, 99918, 30113, -12641, -26665, 86362, -33505, 50661, 26714, 33701, 89012, -91540, 40517, -12716, -57185, -87230, 29914, -59560, 13200, -72723, 58272, 23913, -45586, -96593, -26265, -2141, 31087, 81399, 92511, -34049, 20577, 2803, 26003, 8940, 42117, 40887, -82715, 38269, 40969, -50022, 72088, 21291, -67280, -16523, 90535, 18669, 94342, -39568, -88080, -99486, -20716, 23108, -28037, 63342, 36863, -29420, -44016, 75135, 73415, 16059, -4899, 86893, 43136, -7041, 33483, -67612, 25327, 40830, 6184, 61805, 4247, 81119, -22854, -26104, -63466, 63093, -63685, 60369, 51023, 51644, -16350, 74438, -83514, 99083, 10079, -58451, -79621, 48471, 67131, -86940, 99093, 11855, -22272, -67683, -44371, 9541, 18123, 37766, -70922, 80385, -57513, -76021, -47890, 36154, 72935, 84387, -92681, -88303, -7810, 59902, -90, -64704, -28396, -66403, 8860, 13343, 33882, 85680, 7228, 28160, -14003, 54369, -58893, 92606, -63492, -10101, 64714, 58486, 29948, -44679, -22763, 10151, -56695, 4031, -18242, -36232, 86168, -14263, 9883, 47124, 47271, 92761, -24958, -73263, -79661, -69147, -18874, 29546, -92588, -85771, 26451, -86650, -43306, -59094, -47492, -34821, -91763, -47670, 33537, 22843, 67417, -759, 92159, 63075, 94065, -26988, 55276, 65903, 30414, -67129, -99508, -83092, -91493, -50426, 14349, -83216, -76090, 32742, -5306, -93310, -60750, -60620, -45484, -21108, -58341, -28048, -52803, 69735, 78906, 81649, 32565, -86804, -83202, -65688, -1760, 89707, 93322, -72750, 84134, 71900, -37720, 19450, -78018, 22001, -23604, 26276, -21498, 65892, -72117, -89834, -23867, 55817, -77963, 42518, 93123, -83916, 63260, -2243, -97108, 85442, -36775, 17984, -58810, 99664, -19082, 93075, -69329, 87061, 79713, 16296, 70996, 13483, -74582, 49900, -27669, -40562, 1209, -20572, 34660, 83193, 75579, 7344, 64925, 88361, 60969, 3114, 44611, -27445, 53049, -16085, -92851, -53306, 13859, -33532, 86622, -75666, -18159, -98256, 51875, -42251, -27977, -18080, 23772, 38160, 41779, 9147, 94175, 99905, -85755, 62535, -88412, -52038, -68171, 93255, -44684, -11242, -104, 31796, 62346, -54931, -55790, -70032, 46221, 56541, -91947, 90592, 93503, 4071, 20646, 4856, -63598, 15396, -50708, 32138, -85164, 38528, -89959, 53852, 57915, -42421, -88916, -75072, 67030, -29066, 49542, -71591, 61708, -53985, -43051, 28483, 46991, -83216, 80991, -46254, -48716, 39356, -8270, -47763, -34410, 874, -1186, -7049, 28846, 11276, 21960, -13304, -11433, -4913, 55754, 79616, 70423, -27523, 64803, 49277, 14906, -97401, -92390, 91075, 70736, 21971, -3303, 55333, -93996, 76538, 54603, -75899, 98801, 46887, 35041, 48302, -52318, 55439, 24574, 14079, -24889, 83440, 14961, 34312, -89260, -22293, -81271, -2586, -71059, -10640, -93095, -5453, -70041, 66543, 74012, -11662, -52477, -37597, -70919, 92971, -17452, -67306, -80418, 7225, -89296, 24296, 86547, 37154, -10696, 74436, -63959, 58860, 33590, -88925, -97814, -83664, 85484, -8385, -50879, 57729, -74728, -87852, -15524, -91120, 22062, 28134, 80917, 32026, 49707, -54252, -44319, -35139, 13777, 44660, 85274, 25043, 58781, -89035, -76274, 6364, -63625, 72855, 43242, -35033, 12820, -27460, 77372, -47578, -61162, -70758, -1343, -4159, 64935, 56024, -2151, 43770, 19758, -30186, -86040, 24666, -62332, -67542, 73180, -25821, -27826, -45504, -36858, -12041, 20017, -24066, -56625, -52097, -47239, -90694, 8959, 7712, -14258, -5860, 55349, 61808, -4423, -93703, 64681, -98641, -25222, 46999, -83831, -54714, 19997, -68477, 66073, 51801, -66491, 52061, -52866, 79907, -39736, -68331, 68937, 91464, 98892, 910, 93501, 31295, -85873, 27036, -57340, 50412, 21, -2445, 29471, 71317, 82093, -94823, -54458, -97410, 39560, -7628, 66452, 39701, 54029, 37906, 46773, 58296, 60370, -61090, 85501, -86874, 71443, -72702, -72047, 14848, 34102, 77975, -66294, -36576, 31349, 52493, -70833, -80287, 94435, 39745, -98291, 84524, -18942, 10236, 93448, 50846, 94023, -6939, 47999, 14740, 30165, 81048, 84935, -19177, -13594, 32289, 62628, -90612, -542, -66627, 64255, 71199, -83841, -82943, -73885, 8623, -67214, -9474, -35249, 62254, -14087, -90969, 21515, -83303, 94377, -91619, 19956, -98810, 96727, -91939, 29119, -85473, -82153, -69008, 44850, 74299, -76459, -86464, 8315, -49912, -28665, 59052, -69708, 76024, -92738, 50098, 18683, -91438, 18096, -19335, 35659, 91826, 15779, -73070, 67873, -12458, -71440, -46721, 54856, 97212, -81875, 35805, 36952, 68498, 81627, -34231, 81712, 27100, -9741, -82612, 18766, -36392, 2759, 41728, 69743, 26825, 48355, -17790, 17165, 56558, 3295, -24375, 55669, -16109, 24079, 73414, 48990, -11931, -78214, 90745, 19878, 35673, -15317, -89086, 94675, -92513, 88410, -93248, -19475, -74041, -19165, 32329, -26266, -46828, -18747, 45328, 8990, -78219, -25874, -74801, -44956, -54577, -29756, -99822, -35731, -18348, -68915, -83518, -53451, 95471, -2954, -13706, -8763, -21642, -37210, 16814, -60070, -42743, 27697, -36333, -42362, 11576, 85742, -82536, 68767, -56103, -63012, 71396, -78464, -68101, -15917, -11113, -3596, 77626, -60191, -30585, -73584, 6214, -84303, 18403, 23618, -15619, -89755, -59515, -59103, -74308, -63725, -29364, -52376, -96130, 70894, -12609, 50845, -2314, 42264, -70825, 64481, 55752, 4460, -68603, -88701, 4713, -50441, -51333, -77907, 97412, -66616, -49430, 60489, -85262, -97621, -18980, 44727, -69321, -57730, 66287, -92566, -64427, -14270, 11515, -92612, -87645, 61557, 24197, -81923, -39831, -10301, -23640, -76219, -68025, 92761, -76493, 68554, -77734, -95620, -11753, -51700, 98234, -68544, -61838, 29467, 46603, -18221, -35441, 74537, 40327, -58293, 75755, -57301, -7532, -94163, 18179, -14388, -22258, -46417, -48285, 18242, -77551, 82620, 250, -20060, -79568, -77259, 82052, -98897, -75464, 48773, -79040, -11293, 45941, -67876, -69204, -46477, -46107, 792, 60546, -34573, -12879, -94562, 20356, -48004, -62429, 96242, 40594, 2099, 99494, 25724, -39394, -2388, -18563, -56510, -83570, -29214, 3015, 74454, 74197, 76678, -46597, 60630, -76093, 37578, -82045, -24077, 62082, -87787, -74936, 58687, 12200, -98952, 70155, -77370, 21710, -84625, -60556, -84128, 925, 65474, -15741, -94619, 88377, 89334, 44749, 22002, -45750, -93081, -14600, -83447, 46691, 85040, -66447, -80085, 56308, 44310, 24979, -29694, 57991, 4675, -71273, -44508, 13615, -54710, 23552, -78253, -34637, 50497, 68706, 81543, -88408, -21405, 6001, -33834, -21570, -46692, -25344, 20310, 71258, -97680, 11721, 59977, 59247, -48949, 98955, -50276, -80844, -27935, -76102, 55858, -33492, 40680, 66691, -33188, 8284, 64893, -7528, 6019, -85523, 8434, -64366, -56663, 26862, 30008, -7611, -12179, -70076, 21426, -11261, -36864, -61937, -59677, 929, -21052, 3848, -20888, -16065, 98995, -32293, -86121, -54564, 77831, 68602, 74977, 31658, 40699, 29755, 98424, 80358, -69337, 26339, 13213, -46016, -18331, 64713, -46883, -58451, -70024, -92393, -4088, 70628, -51185, 71164, -75791, -1636, -29102, -16929, -87650, -84589, -24229, -42137, -15653, 94825, 13042, 88499, -47100, -90358, -7180, 29754, -65727, -42659, -85560, -9037, -52459, 20997, -47425, 17318, 21122, 20472, -23037, 65216, -63625, -7877, -91907, 24100, -72516, 22903, -85247, -8938, 73878, 54953, 87480, -31466, -99524, 35369, -78376, 89984, -15982, 94045, -7269, 23319, -80456, -37653, -76756, 2909, 81936, 54958, -12393, 60560, -84664, -82413, 66941, -26573, -97532, 64460, 18593, -85789, -38820, -92575, -43663, -89435, 83272, -50585, 13616, -71541, -53156, 727, -27644, 16538, 34049, 57745, 34348, 35009, 16634, -18791, 23271, -63844, 95817, 21781, 16590, 59669, 15966, -6864, 48050, -36143, 97427, -59390, 96931, 78939, -1958, 50777, 43338, -51149, 39235, -27054, -43492, 67457, -83616, 37179, 10390, 85818, 2391, 73635, 87579, -49127, -81264, -79023, -81590, 53554, -74972, -83940, -13726, -39095, 29174, 78072, 76104, 47778, 25797, -29515, -6493, -92793, 22481, -36197, -65560, 42342, 15750, 97556, 99634, -56048, -35688, 13501, 63969, -74291, 50911, 39225, 93702, -3490, -59461, -30105, -46761, -80113, 92906, -68487, 50742, 36152, -90240, -83631, 24597, -50566, -15477, 18470, 77038, 40223, -80364, -98676, 70957, -63647, 99537, 13041, 31679, 86631, 37633, -16866, 13686, -71565, 21652, -46053, -80578, -61382, 68487, -6417, 4656, 20811, 67013, -30868, -11219, 46, 74944, 14627, 56965, 42275, -52480, 52162, -84883, -52579, -90331, 92792, 42184, -73422, -58440, 65308, -25069, 5475, -57996, 59557, -17561, 2826, -56939, 14996, -94855, -53707, 99159, 43645, -67719, -1331, 21412, 41704, 31612, 32622, 1919, -69333, -69828, 22422, -78842, 57896, -17363, 27979, -76897, 35008, 46482, -75289, 65799, 20057, 7170, 41326, -76069, 90840, -81253, -50749, 3649, -42315, 45238, -33924, 62101, 96906, 58884, -7617, -28689, -66578, 62458, 50876, -57553, 6739, 41014, -64040, -34916, 37940, 13048, -97478, -11318, -89440, -31933, -40357, -59737, -76718, -14104, -31774, 28001, 4103, 41702, -25120, -31654, 63085, -3642, 84870, -83896, -76422, -61520, 12900, 88678, 85547, 33132, -88627, 52820, 63915, -27472, 78867, -51439, 33005, -23447, -3271, -39308, 39726, -74260, -31874, -36893, 93656, 910, -98362, 60450, -88048, 99308, 13947, 83996, -90415, -35117, 70858, -55332, -31721, 97528, 82982, -86218, 6822, 25227, 36946, 97077, -4257, -41526, 56795, 89870, 75860, -70802, 21779, 14184, -16511, -89156, -31422, 71470, 69600, -78498, 74079, -19410, 40311, 28501, 26397, -67574, -32518, 68510, 38615, 19355, -6088, -97159, -29255, -92523, 3023, -42536, -88681, 64255, 41206, 44119, 52208, 39522, -52108, 91276, -70514, 83436, 63289, -79741, 9623, 99559, 12642, 85950, 83735, -21156, -67208, 98088, -7341, -27763, -30048, -44099, -14866, -45504, -91704, 19369, 13700, 10481, -49344, -85686, 33994, 19672, 36028, 60842, 66564, -24919, 33950, -93616, -47430, -35391, -28279, 56806, 74690, 39284, -96683, -7642, -75232, 37657, -14531, -86870, -9274, -26173, 98640, 88652, 64257, 46457, 37814, -19370, 9337, -22556, -41525, 39105, -28719, 51611, -93252, 98044, -90996, 21710, -47605, -64259, -32727, 53611, -31918, -3555, 33316, -66472, 21274, -37731, -2919, 15016, 48779, -88868, 1897, 41728, 46344, -89667, 37848, 68092, -44011, 85354, -43776, 38739, -31423, -66330, 65167, -22016, 59405, 34328, -60042, 87660, -67698, -59174, -1408, -46809, -43485, -88807, -60489, 13974, 22319, 55836, -62995, -37375, -4185, 32687, -36551, -75237, 58280, 26942, -73756, 71756, 78775, -40573, 14367, -71622, -77338, 24112, 23414, -7679, -51721, 87492, 85066, -21612, 57045, 10673, -96836, 52461, -62218, -9310, 65862, -22748, 89906, -96987, -98698, 26956, -43428, 46141, 47456, 28095, 55952, 67323, -36455, -60202, -43302, -82932, 42020, 77036, 10142, 60406, 70331, 63836, 58850, -66752, 52109, 21395, -10238, -98647, -41962, 27778, 69060, 98535, -28680, -52263, -56679, 66103, -42426, 27203, 80021, 10153, 58678, 36398, 63112, 34911, 20515, 62082, -15659, -40785, 27054, 43767, -20289, 65838, -6954, -60228, -72226, 52236, -35464, 25209, -15462, -79617, -41668, -84083, 62404, -69062, 18913, 46545, 20757, 13805, 24717, -18461, -47009, -25779, 68834, 64824, 34473, 39576, 31570, 14861, -15114, -41233, 95509, 68232, 67846, 84902, -83060, 17642, -18422, 73688, 77671, -26930, 64484, -99637, 73875, 6428, 21034, -73471, 19664, -68031, 15922, -27028, 48137, 54955, -82793, -41144, -10218, -24921, -28299, -2288, 68518, -54452, 15686, -41814, 66165, -72207, -61986, 80020, 50544, -99500, 16244, 78998, 40989, 14525, -56061, -24692, -94790, 21111, 37296, -90794, 72100, 70550, -31757, 17708, -74290, 61910, 78039, -78629, -25033, 73172, -91953, 10052, 64502, 99585, -1741, 90324, -73723, 68942, 28149, 30218, 24422, 16659, 10710, -62594, 94249, 96588, 46192, 34251, 73500, -65995, -81168, 41412, -98724, -63710, -54696, -52407, 19746, 45869, 27821, -94866, -76705, -13417, -61995, -71560, 43450, 67384, -8838, -80293, -28937, 23330, -89694, -40586, 46918, 80429, -5475, 78013, 25309, -34162, 37236, -77577, 86744, 26281, -29033, -91813, 35347, 13033, -13631, -24459, 3325, -71078, -75359, 81311, 19700, 47678, -74680, -84113, 45192, 35502, 37675, 19553, 76522, -51098, -18211, 89717, 4508, -82946, 27749, 85995, 89912, -53678, -64727, -14778, 32075, -63412, -40524, 86440, -2707, -36821, 63850, -30883, 67294, -99468, -23708, 34932, 34386, 98899, 29239, -23385, 5897, 54882, 98660, 49098, 70275, 17718, 88533, 52161, 63340, 50061, -89457, 19491, -99156, 24873, -17008, 64610, -55543, 50495, 17056, -10400, -56678, -29073, -42960, -76418, 98562, -88104, -96255, 10159, -90724, 54011, 12052, 45871, -90933, -69420, 67039, 37202, 78051, -52197, -40278, -58425, 65414, -23394, -1415, 6912, -53447, 7352, 17307, -78147, 63727, 98905, 55412, -57658, -32884, -44878, 22755, 39730, 3638, 35111, 39777, 74193, 38736, -11829, -61188, -92757, 55946, -71232, -63032, -83947, 39147, -96684, -99233, 25131, -32197, 24406, -55428, -61941, 25874, -69453, 64483, -19644, -68441, 12783, 87338, -48676, 66451, -447, -61590, 50932, -11270, 29035, 65698, -63544, 10029, 80499, -9461, 86368, 91365, -81810, -71914, -52056, -13782, 44240, -30093, -2437, 24007, 67581, -17365, -69164, -8420, -69289, -29370, 48010, 90439, 13141, 69243, 50668, 39328, 61731, 78266, -81313, 17921, -38196, 55261, 9948, -24970, 75712, -72106, 28696, 7461, 31621, 61047, 51476, 56512, 11839, -96916, -82739, 28924, -99927, 58449, 37280, 69357, 11219, -32119, -62050, -48745, -83486, -52376, 42668, 82659, 68882, 38773, 46269, -96005, 97630, 25009, -2951, -67811, 99801, 81587, -79793, -18547, -83086, 69512, 33127, -92145, -88497, 47703, 59527, 1909, 88785, -88882, 69188, -46131, -5589, -15086, 36255, -53238, -33009, 82664, 53901, 35939, -42946, -25571, 33298, 69291, 53199, 74746, -40127, -39050, 91033, 51717, -98048, 87240, 36172, 65453, -94425, -63694, -30027, 59004, 88660, 3649, -20267, -52565, -67321, 34037, 4320, 91515, -56753, 60115, 27134, 68617, -61395, -26503, -98929, -8849, -63318, 10709, -16151, 61905, -95785, 5262, 23670, -25277, 90206, -19391, 45735, 37208, -31992, -92450, 18516, -90452, -58870, -58602, 93383, 14333, 17994, 82411, -54126, -32576, 35440, -60526, -78764, -25069, -9022, -394, 92186, -38057, 55328, -61569, 67780, 77169, 19546, -92664, -94948, 44484, -13439, 83529, 27518, -48333, 72998, 38342, -90553, -98578, -76906, 81515, -16464, 78439, 92529, 35225, -39968, -10130, -7845, -32245, -74955, -74996, 67731, -13897, -82493, 33407, 93619, 59560, -24404, -57553, 19486, -45341, 34098, -24978, -33612, 79058, 71847, 76713, -95422, 6421, -96075, -59130, -28976, -16922, -62203, 69970, 68331, 21874, 40551, 89650, 51908, 58181, 66480, -68177, 34323, -3046, -49656, -59758, 43564, -10960, -30796, 15473, -20216, 46085, -85355, 41515, -30669, -87498, 57711, 56067, 63199, -83805, 62042, 91213, -14606, 4394, -562, 74913, 10406, 96810, -61595, 32564, 31640, -9732, 42058, 98052, -7908, -72330, 1558, -80301, 34878, 32900, 3939, -8824, 88316, 20937, 21566, -3218, -66080, -31620, 86859, 54289, 90476, -42889, -15016, -18838, 75456, 30159, -67101, 42328, -92703, 85850, -5475, 23470, -80806, 68206, 17764, 88235, 46421, -41578, 74005, -81142, 80545, 20868, -1560, 64017, 83784, 68863, -97516, -13016, -72223, 79630, -55692, 82255, 88467, 28007, -34686, -69049, -41677, 88535, -8217, 68060, -51280, 28971, 49088, 49235, 26905, -81117, -44888, 40623, 74337, -24662, 97476, 79542, -72082, -35093, 98175, -61761, -68169, 59697, -62542, -72965, 59883, -64026, -37656, -92392, -12113, -73495, 98258, 68379, -21545, 64607, -70957, -92254, -97460, -63436, -8853, -19357, -51965, -76582, 12687, -49712, 45413, -60043, 33496, 31539, -57347, 41837, 67280, -68813, 52088, -13155, -86430, -15239, -45030, 96041, 18749, -23992, 46048, 35243, -79450, 85425, -58524, 88781, -39454, 53073, -48864, -82289, 39086, 82540, -11555, 25014, -5431, -39585, -89526, 2705, 31953, -81611, 36985, -56022, 68684, -27101, 11422, 64655, -26965, -63081, -13840, -91003, -78147, -8966, 41488, 1988, 99021, -61575, -47060, 65260, -23844, -21781, -91865, -19607, 44808, 2890, 63692, -88663, -58272, 15970, -65195, -45416, -48444, -78226, -65332, -24568, 42833, -1806, -71595, 80002, -52250, 30952, 48452, -90106, 31015, -22073, 62339, 63318, 78391, 28699, 77900, -4026, -76870, -45943, 33665, 9174, -84360, -22684, -16832, -67949, -38077, -38987, -32847, 51443, -53580, -13505, 9344, -92337, 26585, 70458, -52764, -67471, -68411, -1119, -2072, -93476, 67981, 40887, -89304, -12235, 41488, 1454, 5355, -34855, -72080, 24514, -58305, 3340, 34331, 8731, 77451, -64983, -57876, 82874, 62481, -32754, -39902, 22451, -79095, -23904, 78409, -7418, 77916}, null));

		list.forEach(problem -> {
			start(MILLISECONDS);
			System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.problem, problem.answer, threeSum3(problem.problem)));
			confirm();
		});
	}

	public List<List<Integer>> threeSum1(int[] nums) {

		Set<List<Integer>> result = new HashSet<>();

		int a, b, c;
		int ai, bi, ci;

		for (ai = 0; ai < nums.length - 2; ai += 3) {
			a = nums[ai];

			for (bi = ai + 1; bi < nums.length - 1; bi++) {
				b = nums[bi];

				for (ci = bi + 1; ci < nums.length; ci++) {
					c = nums[ci];

					if (a + b + c == 0) {
						List<Integer> list = new ArrayList<>();
						list.add(a);
						list.add(b);
						list.add(c);
						result.add(new ArrayList<>(list));
					}

				}

			}
		}


		return new ArrayList<>(result);
	}

	public List<List<Integer>> threeSum2(int[] nums) {

		Arrays.sort(nums);
		System.out.println(Arrays.toString(nums));

		List<List<Integer>> result = new ArrayList<>();

		for (int i = 0, lastA = Integer.MIN_VALUE; i < nums.length - 2; i++) {

			int a = nums[i];

			// 优化
			if (lastA == a) continue;

			lastA = a;

			for (int j = i + 1, lastB = Integer.MIN_VALUE; j < nums.length - 1; j++) {
				int b = nums[j];

				// 优化
				if (lastB == b) continue;
				lastB = b;

				// 优化
				if (a + b < nums[nums.length - 1] * -1) break;

				for (int k = nums.length - 1, lastC = Integer.MIN_VALUE; k > j; k--) {
					int c = nums[k];

					// 优化
					if (lastC == c) continue;
					lastC = c;

					// 优化
					if (a + b < c * -1) break;


					if (a + b + c == 0) {
						List<Integer> list = new ArrayList<>();
						list.add(a);
						list.add(b);
						list.add(c);
						result.add(list);
						break;
					}
				}
			}
		}
		return result;
	}


	public List<List<Integer>> threeSum3(int[] nums) {

		Arrays.sort(nums);
		List<List<Integer>> result = new ArrayList<>();
		System.out.println(Arrays.toString(nums));

		for (int i = 0; i < nums.length; i++) {

			int a = nums[i];
			if (a > 0) break;

//			if (i < nums.length - 1 && a == nums[i + 1]) continue;
			if (i > 0 && nums[i] == nums[i - 1]) continue;


			int left = i + 1;
			int right = nums.length - 1;

			while (left < right) {

				int b = nums[left], c = nums[right];

				int abc = a + b + c;

				if (abc == 0) {
					result.add(Arrays.asList(a, b, c));
					while (left < right && b == nums[left]) left++;
					left++;
					while (left < right && c == nums[right]) right--;
					right--;
				} else if (abc > 0) {
					right--;
				} else if (abc < 0) {
					left++;
				}

			}
		}


		return result;
	}


	@Test
	public void test35() {
		class Problem {
			int[] arr;
			int target;
			int answer;

			public Problem(int[] arr, int target, int answer) {
				this.arr = arr;
				this.target = target;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem(new int[]{-1, 2, 1, -4}, 1, 2));
		list.add(new Problem(new int[]{0, 0, 0}, 1, 0));
		list.add(new Problem(new int[]{1, 1, 1, 1}, 0, 3));
		list.add(new Problem(new int[]{1, 1, 1, 0}, 100, 3));
		list.add(new Problem(new int[]{0, 2, 1, -3}, 1, 0));
		list.add(new Problem(new int[]{1, 1, -1, -1, 3}, -1, -1));

		list.forEach(problem -> {
			start(MILLISECONDS);
			System.out.println(String.format("problem: %s:%s, answer: %s, result: %s", Arrays.toString(problem.arr), problem.target, problem.answer, threeSumClosest(problem.arr, problem.target)));
			confirm();
		});
	}

	public int threeSumClosest(int[] nums, int target) {
		Arrays.sort(nums);

		int result = Integer.MAX_VALUE;
		int lastDiff = Integer.MAX_VALUE;

		// 最外层左侧坐标
		for (int i = 0; i < nums.length; i++) {

			int a = nums[i];

			int left = i + 1, right = nums.length - 1;

			// 内曾的移动窗口
			while (left < right) {

				int b = nums[left], c = nums[right];

				// 获取窗口结果
				int sum = a + b + c;
				// 获取差异绝对值
				int diff = Math.abs(sum - target);

				if (diff == 0) return sum;

				// 比较是否更小
				if (diff < lastDiff) {
					result = sum;
					lastDiff = diff;
					// 单方向移动窗口
					if (sum - target < 0) {
						left++;
					} else {
						right--;
					}
					continue;
				}

				// 单方向移动窗口
				if (sum - target < 0) {
					left++;
				} else {
					right--;
				}

			}
		}

		return result;
	}

	@Test
	public void test36() {
		class Problem {
			String input;
			String[] answer;

			public Problem(String input, String[] answer) {
				this.input = input;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem("23", new String[]{"ad", "ae", "af", "bd", "be", "bf", "cd", "ce", "cf"}));

		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.input, Arrays.toString(problem.answer), letterCombinations(problem.input))));
	}

	public List<String> letterCombinations(String digits) {

		List<String> result = new ArrayList<>();

		if (digits.length() == 0) return result;

		String[][] map = new String[][]{new String[]{}, new String[]{}, new String[]{"a", "b", "c"}, new String[]{"d", "e", "f"}, new String[]{"g", "h", "i"}, new String[]{"j", "k", "l"}, new String[]{"m", "n", "o"}, new String[]{"p", "q", "r", "s"}, new String[]{"t", "u", "v"}, new String[]{"w", "x", "y", "z"}};


		char[] chars = digits.toCharArray();

		String[] first = map[chars[0] - '0'];
		result.addAll(Arrays.asList(first));

		for (int i = 1; i < chars.length; i++) {

			String[] data = map[chars[i] - '0'];

			// 进行倍化拼接
			int length = data.length;
			// 内层索引
			int j = 0;
			// 需要使用迭代器进行操作
			ListIterator<String> iterator = result.listIterator();
			while (iterator.hasNext()) {
				String next = iterator.next();
				// 更新当前内容
				iterator.set(next + data[j++]);
				// 倍化部分,可以直接使用add方法在当前迭代位置进行追加
				for (int l = 1; l < length; l++) {
					iterator.add(next + data[j++]);
				}

				if (j >= length) j = 0;
			}
		}

		return result;
	}


	@Test
	public void test37() {
		class Problem {
			int[] problem;
			int target;
			List<List<Integer>> answer;

			public Problem(int[] problem, int target, List<List<Integer>> answer) {
				this.problem = problem;
				this.target = target;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
//		list.add(new Problem(new int[]{1, 0, -1, 0, -2, 2}, 0, null));
//		list.add(new Problem(new int[]{0,0,0,0}, 1, null));
//		list.add(new Problem(new int[]{1,0,-1,0,-2,2}, 0, null));
//		list.add(new Problem(new int[]{-3, -2, -1, 0, 0, 1, 2, 3}, 0, null));
//		list.add(new Problem(new int[]{0,0,0,0}, 0, null));
//		list.add(new Problem(new int[]{-1,0,1,2,-1,-4}, -1, null));
		list.add(new Problem(new int[]{-1, -5, -5, -3, 2, 5, 0, 4}, -7, null));
		list.forEach(problem -> System.out.println(String.format("problem: %s:%s, answer: %s, result: %s", problem.problem, problem.target, problem.answer, fourSum(problem.problem, problem.target))));

	}


	public List<List<Integer>> fourSum(int[] nums, int target) {

		List<List<Integer>> result = new ArrayList<>();

		Arrays.sort(nums);

		if (nums.length < 4) return result;

		for (int ai = 0; ai < nums.length - 3; ai++) {
			// 去重
			if (ai > 0 && nums[ai] == nums[ai - 1]) continue;

			for (int bi = ai + 1; bi < nums.length - 2; bi++) {

				// 保证b不触及a的情况下,进行去重
				if (bi - 1 != ai && nums[bi] == nums[bi - 1]) continue;

				int left = bi + 1;
				int right = nums.length - 1;

				while (left < right) {

					int i = nums[ai] + nums[bi] + nums[left] + nums[right];
					if (i == target) {
						result.add(Arrays.asList(nums[ai], nums[bi], nums[left], nums[right]));

						while (left + 1 < right - 1 && nums[left] == nums[left + 1]) {
							left++;
						}
						left++;

						while (left + 1 < right - 1 && nums[right] == nums[right - 1]) {
							right--;
						}
						right--;
					}
					if (i < target) {
						left++;
					} else if (i > target) {
						right--;
					}
				}
			}
		}

		return result;
	}


	class ListNode {
		int val;
		ListNode next;

		ListNode(int x) {
			val = x;
		}

		public ListNode(String raw) {

			ListNode head = null, tail = null;

			String[] split = raw.trim().split("->");
			this.val = Integer.parseInt(split[0]);
			for (int i = 1; i < split.length; i++) {
				String s = split[i];
				ListNode node = new ListNode(Integer.parseInt(s));
				if (tail == null)
					head = node;
				else
					tail.next = node;

				tail = node;
			}

			next = head;
		}

		@Override
		public String toString() {
			StringBuilder result = new StringBuilder(String.valueOf(val));

			if (next == null) return result.toString();

			ListNode listNode = next;
			do {
				if (result.length() != 0)
					result.append("->");
				result.append(listNode.val);
			} while ((listNode = listNode.next) != null);
			return result.toString();
		}
	}

	@Test
	public void test38() {

		class Problem {
			String raw;
			ListNode node;
			int n;
			String answer;

			public Problem(String raw, String answer, int n) {
				this.raw = raw;
				this.answer = answer;
				this.n = n;
				this.node = new ListNode(raw);
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem("1->2->3->4->5", "1->2->3->5", 2));
		list.add(new Problem("1", "-", 1));
		list.add(new Problem("1->2", "-", 2));
		list.add(new Problem("1->2", "-", 1));
		list.forEach(problem -> System.out.println(String.format("raw: %s:%s, answer: %s, result: %s", problem.raw, problem.n, problem.answer, removeNthFromEnd(problem.node, problem.n))));

	}

	public ListNode removeNthFromEnd(ListNode head, int n) {

		ListNode tail = head;
		ListNode lastTail = head;
		boolean start = false;
		do {

			if (start) {
				lastTail = lastTail.next;
			}

			if (--n < 0) {
				start = true;
			}

		} while ((tail = tail.next) != null);

		if (n < 0) {
			if (lastTail.next != null) {
				lastTail.next = lastTail.next.next;
			}
		} else if (n == 0) {
			if (lastTail.next == null)
				return null;
			else
				return lastTail.next;
		}

		return head;
	}


	@Test
	public void test39() {

		class Problem {
			String problem;
			boolean answer;

			public Problem(String problem, boolean answer) {
				this.problem = problem;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem("()", true));
		list.add(new Problem("()[]{}", true));
		list.add(new Problem("(]", false));
		list.add(new Problem("([)]", false));
		list.add(new Problem("{[]}", true));
		list.add(new Problem("((", false));
		list.add(new Problem("){", false));
		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.problem, problem.answer, isValid(problem.problem))));

	}


	public boolean isValid(String s) {

		if (s.length() % 2 == 1) return false;

		Map<Character, Character> map = new HashMap<>();
		map.put(')', '(');
		map.put('}', '{');
		map.put(']', '[');

		ArrayDeque<Character> stack = new ArrayDeque<>();

		// 将s转换成字节并全部压入左侧栈中
		for (char c : s.toCharArray()) {

			if (map.containsKey(c)) {
				// 栈如果是空的,直接返回false
				if (stack.isEmpty()) return false;

				// 获取对称字符
				Character expect = map.get(c);
				// 弹出栈顶元素,进行匹配
				Character head = stack.removeFirst();
				if (!expect.equals(head)) {
					return false;
				}
			} else {
				// 进行压栈
				stack.addFirst(c);
			}
		}
		return stack.isEmpty();
	}

	@Test
	public void test40() {
		class Problem {
			String raw;
			ListNode l1;
			ListNode l2;
			ListNode answer;

			public Problem(String raw, ListNode l1, ListNode l2, ListNode answer) {
				this.raw = raw;
				this.l1 = l1;
				this.l2 = l2;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();

		list.add(new Problem("1->2->4", new ListNode("1->2->4"), new ListNode("1->3->4"), new ListNode("1->1->2->3->4->4")));

		list.forEach(problem -> System.out.println(String.format("l1: %s, l2: %s, answer: %s, result: %s", problem.l1, problem.l2, problem.answer, mergeTwoLists(problem.l1, problem.l2))));
	}

	public ListNode mergeTwoLists(ListNode l1, ListNode l2) {

		if (l1 == null && l2 == null) return null;

		ListNode head = null, tail = null;

		do {

			ListNode e;

			if (l1 != null && l2 != null) {
				if (l1.val <= l2.val) {
					e = l1;
					l1 = l1.next;
				} else {
					e = l2;
					l2 = l2.next;
				}
			} else {
				if (l1 != null) {
					e = l1;
					l1 = l1.next;
				} else {
					e = l2;
					l2 = l2.next;
				}
			}


			if (tail == null) {
				head = e;
			} else {
				tail.next = e;
			}
			tail = e;

		} while (l1 != null || l2 != null);

		return head;
	}


	@Test
	public void test41() {
		class Problem {
			int problem;
			String answer;

			public Problem(int problem, String answer) {
				this.problem = problem;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem(3, "((()))\",\"(()())\",\"(())()\",\"()(())\",\"()()()\""));

		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.problem, problem.answer, generateParenthesis(problem.problem))));
	}

	public List<String> generateParenthesis(int n) {
		List<String> result = new ArrayList<>();
		gp1(n, n, "", result);
		return result;
	}

	public void gp1(int i, int j, String str, List<String> result) {

		if (i == 0 && j == 0) {
			result.add(str);
			return;
		}

		if (i > 0) {
			gp1(i - 1, j, str + "(", result);
		}

		if (j > 0 && i < j) {
			gp1(i, j - 1, str + ")", result);
		}
	}


	@Test
	public void test42() {
		class Problem {
			List<ListNode> problem;
			ListNode answer;

			public Problem(List<ListNode> problem, ListNode answer) {
				this.problem = problem;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem(Arrays.asList(new ListNode("1->4->5"), new ListNode("1->3->4"), new ListNode("2->6")), new ListNode("1->1->2->3->4->4->5->6")));
		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.problem, problem.answer, mergeKLists(problem.problem.toArray(new ListNode[]{})))));

	}

	public ListNode mergeKLists(ListNode[] lists) {
		if(lists.length == 0) return null;
		if(lists.length == 1) return lists[0];

		List<Integer> valList = new ArrayList<>();
		for (ListNode l : lists) {
			do {
				if(l != null)
					valList.add(l.val);
				else
					break;
			}while ((l = l.next) != null);
		}

		Collections.sort(valList);

		ListNode head = null, tail = null;

		for(int val: valList) {

			ListNode node = new ListNode(val);
			if(tail == null) {
				head = node;
			}else {
				tail.next = node;
			}
			tail = node;
		}

		return head;
	}


	@Test
	public void test43() {

		class Problem{
			ListNode problem;
			ListNode answer;

			public Problem(ListNode problem, ListNode answer) {
				this.problem = problem;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
		list.add(new Problem(new ListNode("1->2->3->4"), new ListNode("2->1->4->3")));

		list.forEach(problem -> System.out.println(String.format("problem: %s, answer: %s, result: %s", problem.problem.toString(), problem.answer, swapPairs(problem.problem))));

	}

	public ListNode swapPairs(ListNode head) {

		if(head == null || head.next == null)
			return head;

		// 交换当前与下一个元素
		ListNode tail = head.next;
		head.next = swapPairs(tail.next);
		tail.next = head;

		return tail;
	}

	@Test
	public void test44() {

		class Problem{
			ListNode problem;
			int k;
			ListNode answer;

			public Problem(ListNode problem, int k, ListNode answer) {
				this.problem = problem;
				this.k = k;
				this.answer = answer;
			}
		}

		List<Problem> list = new ArrayList<>();
//		list.add(new Problem(new ListNode("1->2->3->4->5"), 2, new ListNode("2->1->4->3->5")));
		list.add(new Problem(new ListNode("1->2->3->4->5"), 3, new ListNode("3->2->1->4->5")));
		list.add(new Problem(new ListNode("1->2->3->4"), 4, new ListNode("4->3->2->1")));

		list.forEach(problem -> System.out.println(String.format("problem: %s, k: %s, answer: %s, result: %s", problem.problem.toString(), problem.k, problem.answer, reverseKGroup2(problem.problem, problem.k))));

	}

	public ListNode reverseKGroup1(ListNode head, int k) {

		if(head == null) return null;

		ListNode tail = null, middle = null;

		// 寻找用于交换的尾部
		// 获取头部元素与尾部元素之间的最后一个元素
		for(int i = k; i > 1; i --) {
			if(tail == null) {
				tail = head.next;
			}else {
				middle = tail;
				tail = tail.next;
			}
		}

		// 没有指定元素,直接返回
		if(tail == null) return head;

		// 缓存子节点(下一次递归用)
		ListNode sub = tail.next;

		// 进行元素交换
		if(tail != head.next) {
			// 这里针对k>2的情况,额外添加middle的交换
			tail.next = head.next;
			middle.next = head;
		}else {
			// 对于k=2,使用直接头尾交换
			tail.next = head;
		}

		// 连接子级元素
		head.next = reverseKGroup1(sub, k);
		return tail;
	}

	public ListNode reverseKGroup2(ListNode head, int k) {

		if(head == null) return null;

		ListNode sub = head, tail = head;

		// 进行分组
		int i = 1;
		while (i <= k && tail != null) {

			// 判断是否完成分组
			if(i == k) {
				// 完成分组,进行递归
				rkg2(sub, k);
				// 重置游标
				i = 1;
				sub = tail.next;
			}else {
				i++;
			}

			tail = tail.next;

		}




		return null;
	}
	private void rkg2(ListNode head, int k) {


	}

	public static final ThreadLocal<Long> START_TIME = new ThreadLocal<>();
	public static final ThreadLocal<TimeUnit> TIME_UNIT = new ThreadLocal<>();

	public static void start() {
		start(MILLISECONDS);
	}

	public static void start(TimeUnit timeUnit) {
		switch (timeUnit) {
			case SECONDS:
				START_TIME.set(System.currentTimeMillis() / 1000);
				break;
			case NANOSECONDS:
				START_TIME.set(System.nanoTime());
				break;
			case MILLISECONDS:
			default:
				START_TIME.set(System.currentTimeMillis());
		}
		TIME_UNIT.set(timeUnit);
	}

	public static void confirm() {
		switch (TIME_UNIT.get()) {
			case SECONDS:
				System.out.println(System.currentTimeMillis() / 1000 - START_TIME.get() + "s");
				break;
			case NANOSECONDS:
				System.out.println(System.nanoTime() - START_TIME.get() + "ns");
				break;
			case MILLISECONDS:
			default:
				System.out.println(System.currentTimeMillis() - START_TIME.get() + "ms");
		}

	}


}
