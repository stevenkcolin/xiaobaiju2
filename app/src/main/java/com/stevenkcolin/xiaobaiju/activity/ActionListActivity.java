package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.model.ActionType;
import com.stevenkcolin.xiaobaiju.model.PostAction;
import com.stevenkcolin.xiaobaiju.model.Template;
import com.stevenkcolin.xiaobaiju.service.ActionService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;
import com.stevenkcolin.xiaobaiju.util.DisplayUtil;

import java.util.List;

/**
 * Created by Pengfei on 2016/2/19.
 */
public class ActionListActivity extends BaseActivity {

    private LinearLayout layoutParent;

    private Template template;
    private int PostAction_ADD = 1;
    private int PostAction_Edt = 2;
    private int ActionTypeDetail_view = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_list);

        Bundle b = getIntent().getBundleExtra("info");
        template = (Template)b.getSerializable("template");
        layoutParent = (LinearLayout)findViewById(R.id.parent_layout);

        setTitle(template.getName());

        final Button mButton = (Button)this.findViewById(R.id.add_postAction);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPostAction();
            }
        });

        new GetTemplateDetail().execute();
    }

    //添加PostAction
    public void addPostAction(){
        Intent intent = new Intent(this, PostActionDetail.class);
        intent.setAction("add");

        startActivityForResult(intent,PostAction_ADD);
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
                renderActionTypeList(template.getActionTypeList());
            } else {
                Toast.makeText(ActionListActivity.this, getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    private void renderActionTypeList(List<ActionType> actionTypeList) {
        int i = 0;
        for (ActionType actionType : actionTypeList) {
            if (i != 0) {
                renderDivider(layoutParent);
            }
            renderActionType(actionType, layoutParent);
            i++;
        }

    }

    private void renderActionType(final ActionType actionType, ViewGroup layout) {

        LinearLayout tmpLL = new LinearLayout(this);
        tmpLL.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(DisplayUtil.px2dp(this, 5), DisplayUtil.px2dp(this, 10), 0, DisplayUtil.px2dp(this, 10));
        textView.setLayoutParams(lp);
        textView.setTextSize(20);
        textView.setText(actionType.getName());
        //layout.addView(textView);

        Button btnMore = new Button(this);
        LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBtn.setMargins(DisplayUtil.px2dp(this, 5), DisplayUtil.px2dp(this, 10), 0, DisplayUtil.px2dp(this, 10));
        btnMore.setLayoutParams(lpBtn);
        btnMore.setTextSize(20);
        btnMore.setText(getString(R.string.txt_ActionList_More));

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), ActionTypeDetail.class);
                intent.setAction("view");
                intent.putExtra("ActionType", actionType);
                startActivityForResult(intent, ActionTypeDetail_view);
            }
        });
        //layout.addView(btnMore);

        tmpLL.addView(textView);
        tmpLL.addView(btnMore);
        layout.addView(tmpLL);
        List<PostAction> postActionList = actionType.getPostActionList();
        int i = 0;
        for (PostAction postAction : postActionList) {
            renderDivider(layoutParent);
            ViewGroup relativeGroup = renderPostActions(layout);
            renderPostActionTitle(postAction, relativeGroup);
            renderPostActionImage(postAction, relativeGroup);
            i++;
        }
    }

    private ViewGroup renderPostActions(ViewGroup layout) {
        RelativeLayout relativeLayout = new RelativeLayout(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.px2dp(this, 50));
        relativeLayout.setLayoutParams(lp);
        relativeLayout.setPadding(DisplayUtil.px2dp(this, 15), 0, DisplayUtil.px2dp(this, 15), 0);
        layout.addView(relativeLayout);
        return relativeLayout;
    }

    private void renderPostActionTitle(final PostAction postAction, ViewGroup layout) {
        TextView textView = new TextView(this);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(lp);
        textView.setText(postAction.getTitle());

        final PostAction editPostAction = postAction;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PostActionDetail.class);
                intent.setAction("edit");
                intent.putExtra("PostAction", editPostAction);
                startActivityForResult(intent, PostAction_Edt);
            }
        });

        layout.addView(textView);
    }

     void renderPostActionImage(PostAction postAction, ViewGroup layout) {
        ImageView imageView = new ImageView(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        imageView.setLayoutParams(lp);
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.action_header_temp);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);

        final PostAction editPostAction = postAction;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getBaseContext(), PostActionDetail.class);
                intent.setAction("edit");
                intent.putExtra("PostAction", editPostAction);
                startActivityForResult(intent, PostAction_Edt);
            }
        });
        layout.addView(imageView);
    }

    private void renderDivider(ViewGroup layout) {
        ImageView imageView = new ImageView(this);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        imageView.setPadding(0, DisplayUtil.px2dp(this, 5), 0, DisplayUtil.px2dp(this, 5));
        Drawable drawable = ContextCompat.getDrawable(this, R.drawable.divider);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        layout.addView(imageView);
    }
}
