package week4concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Semaphore;

public  class SemaphoreSum {
    private int sum = 0;

    private Semaphore writeSemaphore = new Semaphore(1);
    public SemaphoreSum () throws InterruptedException {
        writeSemaphore.acquire();

    }
    public void sum() {
        try {

            sum = fibo(36);
        } finally {
            writeSemaphore.release();
        }
    }

    private int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    public int get() {
        try {
            writeSemaphore.acquire();
            return sum;
        } catch (InterruptedException e) {
            e.printStackTrace();
        } finally {
            writeSemaphore.release();

        }
        return sum;
    }

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        SemaphoreSum ss = new SemaphoreSum();
        Thread t = new Thread(() -> ss.sum());
        t.start();

        int result = ss.get();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}