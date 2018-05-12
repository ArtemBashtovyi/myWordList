package com.artembashtovyi.mywordlist.data.async;

import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

/**
 * Created by felix on 5/12/18
 */

public class AppExecutors {

    // simple singleton(class lazy-load)
    private static AppExecutors instance = new AppExecutors();

    private Executor diskIOPool = Executors.newSingleThreadExecutor();
    private Executor mainThreadPool = new MainThreadExecutor();

    private AppExecutors() {
    }

    // not best choice `synchronized` but it enough here
    public static synchronized AppExecutors getInstance() {
        return instance;
    }

    public Executor getDiskExecutor() {
        return diskIOPool;
    }

    public Executor getMainThreadExecutor() {
        return mainThreadPool;
    }

}
