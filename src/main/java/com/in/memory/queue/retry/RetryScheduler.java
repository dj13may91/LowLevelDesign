package com.in.memory.queue.retry;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

public class RetryScheduler {
    private static final int RETRY_TIME = 12000; // in ms

    public void schedule(RetryMessage message) {
        ScheduledExecutorService retryJob = Executors.newScheduledThreadPool(1);
//        retryJob.schedule()
    }
}
