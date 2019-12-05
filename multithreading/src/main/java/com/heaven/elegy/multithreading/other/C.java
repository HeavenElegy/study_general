package com.heaven.elegy.multithreading.other;

/**
 * @author lixiaoxi
 */
public class C {


    public static class C1 {
        public static void main(String[] args) throws InterruptedException {
            Thread thread = new Thread(() -> {
                for (; ; ) {
                    System.out.println(".");
                    try {
                        Thread.sleep(1000L);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                        throw new IllegalStateException(e);
                    }
                }
            });
            thread.start();

            Thread.sleep(3000L);
            System.err.println("停止线程");
            thread.interrupt();
        }
    }




}
