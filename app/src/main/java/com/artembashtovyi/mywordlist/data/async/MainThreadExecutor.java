package com.artembashtovyi.mywordlist.data.async;

import android.os.Handler;
import android.os.Looper;
import android.support.annotation.NonNull;

import java.util.concurrent.Executor;


// Custom executor for UiThread with Handler( MainLooper)
public class MainThreadExecutor implements Executor {



    private final Handler handler = new Handler(Looper.getMainLooper());

    @Override
    public void execute(@NonNull Runnable runnable) {
        handler.post(runnable);
    }
}
