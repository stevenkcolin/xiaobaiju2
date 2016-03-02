package com.stevenkcolin.xiaobaiju.util;

import android.content.Context;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.activity.ActionTypeDetail;
import com.stevenkcolin.xiaobaiju.activity.PostActionDetail;
import com.stevenkcolin.xiaobaiju.model.ActionType;
import com.stevenkcolin.xiaobaiju.model.PostAction;

import java.util.List;

/**
 * Created by linchen on 3/2/16.
 */
public class RenderUtil {
    public RenderUtil() {
        super();
    }

    public static void renderDivider(Context context, ViewGroup layout) {
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        imageView.setLayoutParams(lp);
        imageView.setPadding(0, DisplayUtil.px2dp(context, 5), 0, DisplayUtil.px2dp(context, 5));
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.divider);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        layout.addView(imageView);
    }

    public static void renderPostActionTitle(final Context context, PostAction postAction, ViewGroup layout) {
        TextView textView = new TextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, LinearLayout.LayoutParams.MATCH_PARENT);
        textView.setLayoutParams(lp);
        textView.setText(postAction.getTitle());

        final PostAction editPostAction = postAction;
        textView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActionDetail.class);
                intent.setAction("edit");
                intent.putExtra("PostAction", editPostAction);
                context.startActivity(intent);
            }
        });
        layout.addView(textView);
    }

    public static void renderPostActionImage(final Context context, PostAction postAction, ViewGroup layout) {
        ImageView imageView = new ImageView(context);
        RelativeLayout.LayoutParams lp = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT, RelativeLayout.LayoutParams.WRAP_CONTENT);
        lp.addRule(RelativeLayout.ALIGN_PARENT_RIGHT);
        imageView.setLayoutParams(lp);
        Drawable drawable = ContextCompat.getDrawable(context, R.drawable.action_header_temp);
        imageView.setImageDrawable(drawable);
        imageView.setScaleType(ImageView.ScaleType.FIT_XY);
        imageView.setAdjustViewBounds(true);

        final PostAction editPostAction = postAction;
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, PostActionDetail.class);
                intent.setAction("edit");
                intent.putExtra("PostAction", editPostAction);
                context.startActivity(intent);
            }
        });
        layout.addView(imageView);
    }

    public static ViewGroup renderActionTypeNameMore(final Context context,final ActionType actionType){
        LinearLayout tmpLL = new LinearLayout(context);
        tmpLL.setOrientation(LinearLayout.HORIZONTAL);

        TextView textView = new TextView(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lp.setMargins(DisplayUtil.px2dp(context, 5), DisplayUtil.px2dp(context, 10), 0, DisplayUtil.px2dp(context, 10));
        textView.setLayoutParams(lp);
        textView.setTextSize(20);
        textView.setText(actionType.getName());

        Button btnMore = new Button(context);
        LinearLayout.LayoutParams lpBtn = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        lpBtn.setMargins(DisplayUtil.px2dp(context, 5), DisplayUtil.px2dp(context, 10), 0, DisplayUtil.px2dp(context, 10));
        btnMore.setLayoutParams(lpBtn);
        btnMore.setTextSize(20);
        btnMore.setText(context.getString(R.string.txt_ActionList_More));

        btnMore.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ActionTypeDetail.class);
                intent.setAction("view");
                intent.putExtra("ActionType", actionType);
                context.startActivity(intent);
            }
        });
        tmpLL.addView(textView);
        tmpLL.addView(btnMore);
        return tmpLL;
    }

    public static ViewGroup renderPostActions(final Context context, ViewGroup layout) {
        RelativeLayout relativeLayout = new RelativeLayout(context);
        LinearLayout.LayoutParams lp = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, DisplayUtil.px2dp(context, 50));
        relativeLayout.setLayoutParams(lp);
        relativeLayout.setPadding(DisplayUtil.px2dp(context, 15), 0, DisplayUtil.px2dp(context, 15), 0);
        layout.addView(relativeLayout);
        return relativeLayout;
    }

    public static void renderActionType(final Context context, final ActionType actionType, ViewGroup layout) {
        //给页面添加ActionType名称和更多按钮
        layout.addView(RenderUtil.renderActionTypeNameMore(context, actionType));
        //根据某个ActionType获得postActionList
        List<PostAction> postActionList = actionType.getPostActionList();
        //展示每个postAction
        for (PostAction postAction : postActionList) {
            RenderUtil.renderDivider(context, layout);
            ViewGroup relativeGroup = RenderUtil.renderPostActions(context,layout);
            RenderUtil.renderPostActionTitle(context, postAction, relativeGroup);
            RenderUtil.renderPostActionImage(context, postAction, relativeGroup);
        }
    }
}
