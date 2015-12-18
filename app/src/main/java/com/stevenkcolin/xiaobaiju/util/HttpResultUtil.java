package com.stevenkcolin.xiaobaiju.util;

import com.stevenkcolin.xiaobaiju.exception.ServerException;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Pengfei on 2015/12/9.
 */
public class HttpResultUtil {
    public static Object checkResult(String jsonData) throws JSONException, ServerException {
        JSONObject jsonObject = JSONUtil.fromJSON(jsonData);
        String result = jsonObject.getString("status");
        if (!"success".equals(result)) {
            String message = jsonObject.getString("message");
            throw new ServerException("Exception from Server:" + message);
        }
        if (jsonObject.isNull("result")) {
            return null;
        }
        return jsonObject.get("result");
    }
}
