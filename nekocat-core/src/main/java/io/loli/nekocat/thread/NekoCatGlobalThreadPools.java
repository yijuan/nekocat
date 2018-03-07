package io.loli.nekocat.thread;

import io.loli.nekocat.NekoCatProperties;
import sun.nio.ch.ThreadPool;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.*;

public class NekoCatGlobalThreadPools {
    private static ConcurrentHashMap<String, ThreadPoolExecutor> downloadThreadPoolMap = new ConcurrentHashMap<>();
    private static ConcurrentHashMap<String, ThreadPoolExecutor> consumeThreadPoolMap = new ConcurrentHashMap<>();


    public static ThreadPoolExecutor getDownloadExecutor(NekoCatProperties properties, String spiderName) {
        String key = spiderName + "-" + properties.getName();
        return downloadThreadPoolMap.computeIfAbsent(properties.getName(), name -> new ThreadPoolExecutor(properties.getDownloadMinPoolSize(), properties.getDownloadMaxPoolSize(),
                0, TimeUnit.MILLISECONDS,
                properties.getDownloadMaxQueueSize() == 0 ? new SynchronousQueue<>() :
                        new LinkedBlockingQueue<>(properties.getDownloadMaxQueueSize()), new NekoCatNamedThreadFactory(properties.getDownloadThreadName(), key)));
    }

    public static ThreadPoolExecutor getConsumeExecutor(NekoCatProperties properties, String spiderName) {
        String key = spiderName + "-" + properties.getName();
        return consumeThreadPoolMap.computeIfAbsent(properties.getName(), name -> new ThreadPoolExecutor(properties.getConsumeMinPoolSize(), properties.getConsumeMaxPoolSize(),
                0, TimeUnit.MILLISECONDS,
                properties.getConsumeMaxQueueSize() == 0 ? new SynchronousQueue<>() :
                        new LinkedBlockingQueue<>(properties.getConsumeMaxQueueSize()), new NekoCatNamedThreadFactory(properties.getConsumeThreadName(), key)));
    }


}
