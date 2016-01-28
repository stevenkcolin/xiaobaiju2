package com.stevenkcolin.xiaobaiju.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;

import com.stevenkcolin.xiaobaiju.common.ActivityCollector;
import com.stevenkcolin.xiaobaiju.report.Report;

/**
 * Created by Pengfei on 2015/12/9.
 */
public class BaseActivity extends AppCompatActivity {
    public static Report mReport = Report.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseActivity", getClass().getSimpleName());
        ActivityCollector.addActivity(this);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        ActivityCollector.removeActivity(this);
    }
}
