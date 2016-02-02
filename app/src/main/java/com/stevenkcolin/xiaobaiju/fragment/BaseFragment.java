package com.stevenkcolin.xiaobaiju.fragment;

import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;

import com.stevenkcolin.xiaobaiju.report.Report;

/**
 * Created by Pengfei on 2016/2/2.
 */
public class BaseFragment extends Fragment {
    public static Report mReport = Report.getInstance();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d("BaseFragment", getClass().getSimpleName());
    }

    public void fresh(){

    }
}
