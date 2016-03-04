package com.stevenkcolin.xiaobaiju.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.model.ActionType;
import com.stevenkcolin.xiaobaiju.model.PostAction;
import com.stevenkcolin.xiaobaiju.util.RenderUtil;

import java.util.List;

public class ActionTypeDetail extends BaseActivity {
    private ActionType mActionType;
    private LinearLayout mLayoutParent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_type_detail);
        //从父页面获得mActionType
        mActionType = getIntent().getSerializableExtra("ActionType") == null ? null : (ActionType)getIntent().getSerializableExtra("ActionType");
        //mLayout=动态加载的主页面
        mLayoutParent = (LinearLayout)findViewById(R.id.parent_ActionType_layout);
        //设置页面标题为该ActionType的名字
        setTitle(mActionType.getName());
        //从ActionType中获得postActionList。
        List<PostAction> postActionList = mActionType.getPostActionList();
        //在mLayoutParent上面一个个添加PostActions
        for (PostAction postAction : postActionList) {
            //先加载一个分隔符
            RenderUtil.renderDivider(this,mLayoutParent);
            //再加载一个PostAction的Title和Image
            ViewGroup relativeGroup = RenderUtil.renderPostActions(this,mLayoutParent);
            RenderUtil.renderPostActionTitle(this,postAction,relativeGroup);
            RenderUtil.renderPostActionImage(this,postAction, relativeGroup);
        }
        //最后一行加载一个分隔符
        RenderUtil.renderDivider(this,mLayoutParent);
        //右下方的加号按钮加上addPostAction
        final Button mButton = (Button)this.findViewById(R.id.add_postAction);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPostAction();
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_action_type_detail, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();
        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    //添加PostAction
    public void addPostAction(){
        Intent intent = new Intent(this, PostActionDetail.class);
        intent.setAction("add");
        startActivity(intent);
    }
}
