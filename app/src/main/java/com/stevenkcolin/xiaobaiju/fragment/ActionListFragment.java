package com.stevenkcolin.xiaobaiju.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TabHost;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.activity.LoginActivity;
import com.stevenkcolin.xiaobaiju.activity.TemplateDetailActivity;
import com.stevenkcolin.xiaobaiju.model.Template;
import com.stevenkcolin.xiaobaiju.service.ActionService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;

import java.util.List;

/**
 * Created by Pengfei on 2016/2/3.
 */
public class ActionListFragment extends BaseFragment {
    private TabHost mTabHost;
    private TableLayout mTableLayoutMy;
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_action_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        mTabHost = (TabHost)getView().findViewById(R.id.tabHost);
        mTabHost.setup();
        mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(getString(R.string.txt_Template_follow)).setContent(R.id.my));
        mTabHost.addTab(mTabHost.newTabSpec("tab2").setIndicator(getString(R.string.txt_Template_other)).setContent(R.id.other));

        mTableLayoutMy = (TableLayout)getView().findViewById(R.id.my);

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
                renderTemplates(templateList);
            } else {
                Toast.makeText(getActivity(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void renderTemplateTab(List<Template> templateList) {
        mTabHost.setup();
        for (Template template: templateList) {
            Intent intent = new Intent(getContext(), LoginActivity.class);
            mTabHost.addTab(mTabHost.newTabSpec("tab1").setIndicator(template.getName()).setContent(R.id.my));
        }

    }

    private void renderTemplates(List<Template> templateList) {
        int i = 0;
        LayoutInflater inflater = getLayoutInflater(null);
        int btnId;
        TableRow tableRow = null;
        for (final Template template : templateList) {
            if (i % 2 == 0) {
                tableRow = (TableRow)inflater.inflate(R.layout.sub_row_template, mTableLayoutMy, false);
                btnId = R.id.template_left;
            } else {
                btnId = R.id.template_right;
            }
            Button btn = (Button)tableRow.findViewById(btnId);
            btn.setTransformationMethod(null);
            btn.setText(template.getName());
            btn.setVisibility(View.VISIBLE);
            btn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getActivity(), TemplateDetailActivity.class);
                    Bundle b = new Bundle();
                    b.putSerializable("template", template);
                    intent.putExtra("info", b);
                    startActivity(intent);
                }
            });
            if (i % 2 == 0) {
                mTableLayoutMy.addView(tableRow);
            }
            i++;
        }
        if (i % 2 == 0) {
            mTableLayoutMy.addView(tableRow);
        }
    }

}
