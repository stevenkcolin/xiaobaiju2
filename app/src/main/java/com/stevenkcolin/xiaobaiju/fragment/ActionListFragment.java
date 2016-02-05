package com.stevenkcolin.xiaobaiju.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TabHost;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.activity.LoginActivity;
import com.stevenkcolin.xiaobaiju.model.Template;
import com.stevenkcolin.xiaobaiju.service.ActionService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;

import java.util.List;

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
        new GetTemplate().execute();
    }

    class GetTemplate extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog barProgressDialog;
        private List<Template> templateList;

        @Override
        protected void onPreExecute() {
            barProgressDialog = DialogUtil.showWaitDialog(getActivity(), getString(R.string.please_wait));
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ActionService actionService = new ActionService();
                templateList = actionService.getAllTemplate();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            barProgressDialog.dismiss();
            if (result) {
                renderTemplateTab(templateList);
            } else {
                Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void renderTemplateTab(List<Template> templateList) {
        mTabHost.setup();
        for (Template template: templateList) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(template.getName()).setContent(R.id.linearLayout));
        }

    }
}
