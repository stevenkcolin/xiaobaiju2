package com.stevenkcolin.xiaobaiju.common;

/**
 * Created by Pengfei on 2015/12/9.
 */
public interface HttpCallbackListener {

    void onFinish(Object result);

    void onError(Exception e);
}
