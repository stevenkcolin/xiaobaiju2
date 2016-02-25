package com.stevenkcolin.xiaobaiju.util;

import android.content.Context;

/**
 * Created by Pengfei on 2016/2/24.
 */
public class DisplayUtil {
    public static int px2dp(Context context, float px) {
        float density = context.getResources().getDisplayMetrics().density;
        return (int)(density * px);
    }
}
