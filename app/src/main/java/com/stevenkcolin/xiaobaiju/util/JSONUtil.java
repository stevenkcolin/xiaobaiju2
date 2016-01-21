package com.stevenkcolin.xiaobaiju.util;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.Collection;
import java.util.Map;

/**
 * Created by Pengfei on 2015/9/25.
 */

public class JSONUtil {
    /*public static String stringify(Object object, String[] excludeFields) {
        JsonConfig jsonConfig = new JsonConfig();
//        jsonConfig.registerJsonValueProcessor(Date.class, new JSONDateValueProcessor(GeneralConstant.DATE_FORMAT));
        jsonConfig.setIgnoreDefaultExcludes(false);
        jsonConfig.setExcludes(excludeFields);
        return new JSONArray().fromObject(object, jsonConfig).toString();
    }


    public static String stringify(Object object) {
        return stringify(object, new String[]{});
    }*/

    public static String stringifySingle(Map map) {
        return new JSONObject(map).toString();
    }

    public static String stringifyArray(Collection col) throws JSONException {
        return new JSONArray(col).toString();
    }

    public static JSONObject fromJSON(String json) throws JSONException{
        JSONObject jsonObject = new JSONObject(json);
        return jsonObject;
    }
}
