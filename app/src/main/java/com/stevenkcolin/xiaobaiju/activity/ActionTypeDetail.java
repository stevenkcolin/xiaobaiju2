package com.stevenkcolin.xiaobaiju.activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.model.ActionType;
import com.stevenkcolin.xiaobaiju.model.PostAction;
import com.stevenkcolin.xiaobaiju.util.DisplayUtil;

import java.util.List;

public class ActionTypeDetail extends AppCompatActivity {
    private ActionType mActionType;
    private LinearLayout layoutParent;

    private int PostAction_ADD = 1;
    private int PostAction_Edt = 2;
    private int ActionTypeDetail_view = 3;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_action_type_detail);

        mActionType = getIntent().getSerializableExtra("ActionType") == null ? null : (ActionType)getIntent().getSerializableExtra("ActionType");
        layoutParent = (LinearLayout)findViewById(R.id.parent_ActionType_layout);

        List<PostAction> postActionList = mActionType.getPostActionList();
        int i = 0;
        for (PostAction postAction : postActionList) {
            renderDivider(layoutParent);
            ViewGroup relativeGroup = renderPostActions(layoutParent);
            renderPostActionTitle(postAction, relativeGroup);
            renderPostActionImage(postAction, relativeGroup);
            i++;
        }
        renderDivider(layoutParent);

        final Button mButton = (Button)this.findViewById(R.id.add_postAction);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addPostAction();
            }
        });


    }

    //添加PostAction
    public void addPostAction(){
        Intent intent = new Intent(this, PostActionDetail.class);
        intent.setAction("add");

        startActivityForResult(intent, PostAction_ADD);
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

    private void renderPostActionImage(PostAction postAction, ViewGroup layout) {
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
}
