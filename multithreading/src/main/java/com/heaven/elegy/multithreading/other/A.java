package com.heaven.elegy.multithreading.other;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

/**
 * @author lixiaoxi
 */
public class A {

    private static BlockingQueue<String> blockingQueue = new ArrayBlockingQueue<>(1);


    public static void main(String[] args) throws InterruptedException {

        Thread thread = new MyThread();
        thread.start();

        System.out.println("1");
        blockingQueue.offer("啊");
        System.out.println("2");
        blockingQueue.offer("哈");
        System.out.println("3");

        Thread.sleep(1*1000);
        thread.interrupt();


        Thread.sleep(10*1000);

    }


    private static class MyThread extends Thread {

        @Override
        public void run() {
            try {
                while (true) {
                    String take = blockingQueue.take();
                    System.out.println("接收到数据: " + take);
                    Thread.sleep(1000);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
//                Thread.currentThread().interrupt();
            }
        }
    }


}
