package com.heaven.elegy.common.util;

import java.util.Hashtable;

/**
 * 用于对{@link java.util.Hashtable}类进行相关学习
 * <p>{@link Hashtable}拥有如下特点</p>
 * <ol>
 *     <li>大多数方法使用了<code>synchronized</code>进行修饰。所以{@link Hashtable}是线程安全的</li>
 *     <li>对于元素储存。使用其要储存的对象的hashCode，并进行整理。储存在私有成员变量table中</li>
 *     <li>对于在元素储存过程中。hashCode重复的强狂。使用了链表，将上一个元素储存为当前元素的next。并在私有局部变量table的对应的hashCode位置替换为当前元素</li>
 * </ol>
 * @author lixiaoxi
 */
public class HashTableInfo {

    /**
     * 通过添加元素。可以在断点位置处观察到内部储存结构
     * <p><b>当使用IDEA只能看到{@link Hashtable}只作为Map展开时，可能无法看到类中的其他成员变量，此时只需要右键此变量->View as->Object即可</b></p>
     */
    public static void main(String[] args) {

        Hashtable<HashtableKey, String> hashtable = new Hashtable<>();
        hashtable.put(new HashtableKey("a1"), "a1");
        hashtable.put(new HashtableKey("a2"), "a2");
        hashtable.put(new HashtableKey("b1"), "b1");
        hashtable.put(new HashtableKey("b2"), "b2");

        // 添加断点
        System.out.println(hashtable);
    }


    /**
     * 修改了{@link Object#hashCode()}。使其能对多个key返回相同的hashCode
     */
    public static class HashtableKey {

        private String key;

        public HashtableKey(String key) {
            this.key = key;
        }

        /**
         * 重写后，返回第一个字符作为hashCode
         */
        @Override
        public int hashCode() {
            return key.charAt(0);
        }
    }
}
