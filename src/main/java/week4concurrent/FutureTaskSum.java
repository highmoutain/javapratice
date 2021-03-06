package week4concurrent;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;
import java.util.concurrent.atomic.AtomicInteger;

public class FutureTaskSum {

    static class Sum implements Callable<Integer> {

        @Override
        public Integer call() throws Exception {
            return fibo(36);
        }

        private int fibo(int a) {
            if ( a < 2) {
                return 1;
            }
            return fibo(a-1) + fibo(a-2);
        }
    }

    public static void main(String[] args) throws InterruptedException, ExecutionException {

        long start=System.currentTimeMillis();

        // 在这里创建一个线程或线程池，
        // 异步执行 下面方法
        FutureTask<Integer> sum = new FutureTask<>(new Sum());

        Thread sumT = new Thread(sum);
        sumT.start();

        int result = sum.get();


        // 确保  拿到result 并输出
        System.out.println("异步计算结果为："+result);

        System.out.println("使用时间："+ (System.currentTimeMillis()-start) + " ms");

        // 然后退出main线程
    }

    private static int sum() {
        return fibo(36);
    }

    private static int fibo(int a) {
        if ( a < 2)
            return 1;
        return fibo(a-1) + fibo(a-2);
    }
}
