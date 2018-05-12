package com.artembashtovyi.mywordlist;

import android.app.Application;

import com.squareup.leakcanary.LeakCanary;


public class MyApp extends Application {

    // TODO : 1) ability to add the words by groups 2) save current position of screen 3) ability to export all words.
    @Override
    public void onCreate() {
        super.onCreate();
        if (LeakCanary.isInAnalyzerProcess(this)) {
            return;
        }
        LeakCanary.install(this);
    }
}