package week4concurrent;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public  class LockWithConditionSum {
    public Lock lock = new ReentrantLock();
    public Condition con = lock.newCondition();

    private static CountDownLatch cdLatch ;
    private static int value;
    private void sum() {
        lock.lock();
        try {
            value = fibo(36);
            con.signal();
        } finally {
            lock.unlock();
        }
    }

    private static int fibo(int a) {
        if ( a < 2) {
            return 1;
        }
        return fibo(a-1) + fibo(a-2);
    }


    public  int getValue() throws InterruptedException {
        lock.lock();
        try {
            con.await();
        } finally {
            lock.unlock();
        }

        return value;
    }

    public static void main(String[] args) throws InterruptedException {
        long start=System.currentTimeMillis();
        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        LockWithConditionSum lwcSum = new LockWithConditionSum();

        Thread t = new Thread(() -> lwcSum.sum());
        t.start();

        int result = lwcSum.getValue();

        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");
    }
}