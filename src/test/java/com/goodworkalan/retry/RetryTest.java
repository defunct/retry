package com.goodworkalan.retry;

import static org.testng.Assert.assertEquals;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

import org.testng.annotations.Test;

/**
 * Unit tests for the {@link Retry} class.
 *
 * @author Alan Gutierrez
 */
public class RetryTest {
    /** Construct for the coverage. */
    @Test
    public void consructor() {
        new Retry();
    }

    /** Test retry. */
    @Test
    public void procedure() throws InterruptedException {
        Thread thread = new Thread() {
            public void run() {
                Retry.retry(new Retry.Procedure() {
                    int count;
                    public void retry() throws InterruptedException {
                        if (count == 0) {
                            count++;
                            synchronized (this) {
                                wait();
                            }
                        }
                    }
                });
            }
        };
        thread.start();
        thread.interrupt();
        thread.join();
    }
    
    /** Test procedure. */
    @Test
    public void function() throws InterruptedException {
        Thread thread = new Thread() {
            public void run() {
                String a = Retry.retry(new Retry.Function<String>() {
                    int count;
                    public String retry() throws InterruptedException {
                        if (count == 0) {
                            count++;
                            synchronized (this) {
                                wait();
                            }
                        }
                        return "A";
                    }
                });
                assertEquals(a, "A");
            }
        };
        thread.start();
        thread.interrupt();
        thread.join();
    }
    
    /** Test future. */
    @Test
    public void future() throws InterruptedException {
        final FutureTask<Integer> future = new FutureTask<Integer>(new Callable<Integer>() {
            public Integer call() {
                return 1;
            }
        });
        Thread consumer = new Thread() {
            @Override
            public void run() {
                int i = 0;
                try {
                    i = Retry.retry(future);
                } catch (ExecutionException e) {
                }
                assertEquals(i, 1);
            }
        };
        consumer.start();
        consumer.interrupt();
        Thread thread = new Thread(future);
        thread.start();
        consumer.join();
        thread.join();
    }
}
