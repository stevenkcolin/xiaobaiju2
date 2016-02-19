package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.model.Template;
import com.stevenkcolin.xiaobaiju.service.ActionService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;

/**
 * Created by Pengfei on 2016/2/19.
 */
public class ActionListActivity extends BaseActivity {

    private Template template;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        Bundle b = getIntent().getBundleExtra("info");
        template = (Template)b.getSerializable("template");

        setTitle(template.getName());

        new GetTemplateDetail().execute();
    }

    class GetTemplateDetail extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog barProgressDialog;

        @Override
        protected void onPreExecute() {
            barProgressDialog = DialogUtil.showWaitDialog(ActionListActivity.this, getString(R.string.please_wait));
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                ActionService actionService = new ActionService();
                template = actionService.getTemplateDetail(template.get_id());
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            barProgressDialog.dismiss();
            if (result) {
//                renderTemplates(templateList);
            } else {
                Toast.makeText(ActionListActivity.this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }
    }
}
