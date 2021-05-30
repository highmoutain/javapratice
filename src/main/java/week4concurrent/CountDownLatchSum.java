package week4concurrent;

import java.util.concurrent.CountDownLatch;

public  class CountDownLatchSum {
    private static CountDownLatch cdLatch ;
    private static int value;
    private static void sum() {
        value = fibo(36);
        cdLatch.countDown();
    }

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }

    public void setCdLatch(CountDownLatch cdLatch) {
        this.cdLatch = cdLatch;
    }

    public static int getValue() throws InterruptedException {
        cdLatch.await();
        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法

        CountDownLatchSum cdlSum = new CountDownLatchSum();
        CountDownLatch cdLatch = new CountDownLatch(1);
        cdlSum.setCdLatch(cdLatch);
        Thread t = new Thread(() -> cdlSum.sum());
        t.start();

        int result = cdlSum.getValue();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}