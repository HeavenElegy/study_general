package com.heaven.elegy.multithreading.other;

import java.util.concurrent.*;

/**
 * @author lixiaoxi
 */
public class B {

    private static BlockingQueue<String> queue = new ArrayBlockingQueue<>(1);

    public static void main(String[] args) throws ExecutionException, InterruptedException {

        ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1);
        Thread thread = Thread.currentThread();
        executorService.schedule(
                () -> {
                    thread.interrupt();
                    executorService.shutdown();
                },
                10,
                TimeUnit.SECONDS
        );

        Future<String> future = executorService.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                return "哈哈";
            }
        });


        System.out.println(future.get());




//        ((Runnable) () -> {
//            try {
//                queue.take();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//
//        }).run();

    }
}
