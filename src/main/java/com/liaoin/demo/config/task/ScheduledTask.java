package com.liaoin.demo.config.task;

import java.util.concurrent.ScheduledFuture;

/**
 * @author mc
 * Create date 2020/8/25 17:38
 * Version 1.0
 * Description  ScheduledFuture的包装类。ScheduledFuture是ScheduledExecutorService定时任务线程池的执行结果
 */
public class ScheduledTask {
    volatile ScheduledFuture<?> future;

    /**
     * 取消定时任务
     */
    public void cancel() {
        ScheduledFuture<?> future = this.future;
        if (future != null) {
            future.cancel(true);
        }
    }
}
