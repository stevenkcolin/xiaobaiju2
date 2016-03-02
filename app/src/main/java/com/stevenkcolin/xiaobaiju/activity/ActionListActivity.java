package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.model.ActionType;
import com.stevenkcolin.xiaobaiju.model.Template;
import com.stevenkcolin.xiaobaiju.service.ActionService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;
import com.stevenkcolin.xiaobaiju.util.RenderUtil;

import java.util.List;

/**
 * Created by Pengfei on 2016/2/19.
 */
public class ActionListActivity extends BaseActivity {

    private LinearLayout mLayoutParent;
    private Template mTemplate;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);
        //获得从父页面的传递来的数据。
        Bundle b = getIntent().getBundleExtra("info");
        //获得template model
        mTemplate = (Template)b.getSerializable("template");
        //获得动态加载的父页面
        mLayoutParent = (LinearLayout)findViewById(R.id.parent_layout);
        //设置页面的标题为template的名称
        setTitle(mTemplate.getName());
        //给右下角的圆形加号添加事件
        final Button mButton = (Button)this.findViewById(R.id.add_postAction);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPostAction();
            }
        });
        //获得TemplateDetail详情
        new GetTemplateDetail().execute();
    }

    class GetTemplateDetail extends AsyncTask<Void, Void, Boolean> {
        private ProgressDialog barProgressDialog;
        @Override
        protected void onPreExecute() {
            //显示请等待的滚动条
            barProgressDialog = DialogUtil.showWaitDialog(ActionListActivity.this, getString(R.string.please_wait));
        }

        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                //调用后台接口，得到mTemplate
                ActionService actionService = new ActionService();
                mTemplate = actionService.getTemplateDetail(mTemplate.get_id());
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            barProgressDialog.dismiss();
            if (result) {
                //将从后台得到mTemplate在前端展示出来
                renderActionTypeList(mTemplate.getActionTypeList());
            } else {
                Toast.makeText(ActionListActivity.this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }
    }
    //显示整个ActionTypeList
    private void renderActionTypeList(List<ActionType> actionTypeList) {
        int i = 0;
        //依次显示ActionType
        for (ActionType actionType : actionTypeList) {
            if (i != 0) {
                RenderUtil.renderDivider(this, mLayoutParent);
            }
            //显示renderActionType
            RenderUtil.renderActionType(this,actionType,mLayoutParent);
            i++;
        }
    }

    //添加PostAction
    public void addPostAction(){
        Intent intent = new Intent(this, PostActionDetail.class);
        intent.setAction("add");
        startActivity(intent);
    }
}
