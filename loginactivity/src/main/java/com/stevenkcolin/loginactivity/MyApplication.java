package com.stevenkcolin.loginactivity;

import android.app.Application;

/**
 * Created by linchen on 11/25/15.
 */
public class MyApplication extends Application {

    @Override
    public void onCreate() {
        super.onCreate();
        Thread.setDefaultUncaughtExceptionHandler(new ExceptionCatch());
    }
}
