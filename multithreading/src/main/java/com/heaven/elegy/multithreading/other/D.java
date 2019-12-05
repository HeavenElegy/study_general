package com.heaven.elegy.multithreading.other;

/**
 * @author lixiaoxi
 */
public class D {

    public void a() {
        while (true) {
            c();
        }
    }

    public void b() {
        for(;;) {
            c();
        }
    }
    public void c() {
        System.out.println();
    }
}
