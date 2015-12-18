package com.stevenkcolin.xiaobaiju.util;

import android.app.ProgressDialog;
import android.content.Context;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class DialogUtil {
    public static ProgressDialog showWaitDialog(Context ctx, String message) {
        ProgressDialog progressDialog = new ProgressDialog(ctx);
        progressDialog.setMessage(message);
        progressDialog.setProgressStyle(progressDialog.STYLE_SPINNER);
        progressDialog.show();
        return progressDialog;
    }
}
