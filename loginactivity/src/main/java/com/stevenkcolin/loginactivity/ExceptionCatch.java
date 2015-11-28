package com.stevenkcolin.loginactivity;

import android.util.Log;

/**
 * Created by linchen on 11/25/15.
 */
public class ExceptionCatch implements Thread.UncaughtExceptionHandler {
    @Override
    public void uncaughtException(Thread thread, Throwable ex) {
        System.out.println(ex);
        Log.e("hahahaha", "uncaughtException " + ex.getMessage());
    }
}
