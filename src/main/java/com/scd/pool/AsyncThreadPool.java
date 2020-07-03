package com.scd.pool;

import com.alibaba.ttl.TtlCallable;
import com.alibaba.ttl.TtlRunnable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Future;

/**
 * @author James
 */
@Component
public class AsyncThreadPool {

    @Autowired
    @Qualifier("asyncPool")
    private ExecutorService asyncThreadPool;

    public void execute(Runnable task) {
        Runnable ttlRunnable = TtlRunnable.get(task);
        asyncThreadPool.execute(ttlRunnable);
    }

    public <T> Future<T> submit(Callable<T> task) {
        Callable<T> callable = TtlCallable.get(task);
        return asyncThreadPool.submit(callable);
    }
}
