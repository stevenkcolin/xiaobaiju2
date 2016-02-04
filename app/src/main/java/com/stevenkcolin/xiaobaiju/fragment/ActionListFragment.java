package com.stevenkcolin.xiaobaiju.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;

import com.stevenkcolin.xiaobaiju.R;

/**
 * Created by Pengfei on 2016/2/3.
 */
public class ActionListFragment extends BaseFragment {
    private TabHost mTabHost;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTabHost = (TabHost)getView().findViewById(R.id.tabHost);
    }
}
