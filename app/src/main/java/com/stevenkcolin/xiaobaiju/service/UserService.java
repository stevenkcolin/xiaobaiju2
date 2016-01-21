package com.stevenkcolin.xiaobaiju.service;

import android.content.Context;

import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.util.FileUtil;
import com.stevenkcolin.xiaobaiju.util.HttpUtil;
import com.stevenkcolin.xiaobaiju.util.JSONUtil;
import com.stevenkcolin.xiaobaiju.util.MD5Util;
import com.stevenkcolin.xiaobaiju.model.User;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Pengfei on 2016/1/14.
 */
public class UserService {

    public boolean login(String phone, String password, Context ctx) throws Exception {
        Map<String, String> result = new HashMap<>();
        Map<String, String> map = new HashMap<>();
        map.put("phone", phone);
        map.put("password", password);
        JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + GeneralConstant.USER_LOGIN_URI, "POST", JSONUtil.stringifySingle(map));
        if (jsonArray.length() == 0) {
            return false;
        }
        JSONObject jsonObject = (JSONObject)jsonArray.get(0);

        User user = User.getUser();
        user.setId(jsonObject.getString("_id"));
        user.setPhone(jsonObject.getString("phone"));

        FileUtil.write(ctx, GeneralConstant.FILE_NAME_ACCOUNT, "0" + "\n" + phone + "\n" + MD5Util.GetMD5Code(password));
        return true;
    }

    public boolean register(String phone, String password, Context ctx) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        map.put("password", password);
        JSONObject jsonObject = (JSONObject)HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + GeneralConstant.USER_CREATE_URI, "POST", JSONUtil.stringifySingle(map));
        FileUtil.write(ctx, GeneralConstant.FILE_NAME_ACCOUNT, "0" + "\n" + phone + "\n" + MD5Util.GetMD5Code(password));
        User user = User.getUser();
        user.setId(jsonObject.getString("_id"));
        user.setPhone(jsonObject.getString("phone"));
        return true;
    }

    public boolean register3rd(String loginFrom, String loginAccount, String name, Context ctx) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginFrom", loginFrom);
        map.put("loginAccount", loginAccount);
        map.put("name", name);
        JSONObject jsonObject = (JSONObject)HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + GeneralConstant.USER_CREATE_URI, "POST", JSONUtil.stringifySingle(map));
        FileUtil.write(ctx, GeneralConstant.FILE_NAME_ACCOUNT, "1" + "\n" + loginFrom + "\n" + loginAccount + "\n" + name);
        User user = User.getUser();
        user.setId(jsonObject.getString("_id"));
        user.setLoginFrom(loginFrom);
        user.setLoginAccount(loginAccount);
        user.setName(name);
        return true;
    }

    public boolean checkPhoneExist(String phone) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("phone", phone);
        JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + GeneralConstant.USER_FIND_URI, "POST", JSONUtil.stringifySingle(map));
        if (jsonArray.length() > 0) {
            return false;
        }
        return true;
    }

    public boolean login3rdAcountExist(String loginFrom, String loginAccount, String name, Context ctx) throws Exception {
        Map<String, String> map = new HashMap<String, String>();
        map.put("loginFrom",loginFrom);
        map.put("loginAccount",loginAccount);
        JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.USER_URI + GeneralConstant.USER_LOGIN_OTHER_URI, "POST", JSONUtil.stringifySingle(map));
        if (jsonArray.length() > 0) {
            JSONObject jsonObject = jsonArray.getJSONObject(0);
            User user = User.getUser();
            user.setId(jsonObject.getString("_id"));
            user.setLoginFrom(loginFrom);
            user.setLoginAccount(loginAccount);
            user.setName(name);
            FileUtil.write(ctx, GeneralConstant.FILE_NAME_ACCOUNT, "1" + "\n" + loginFrom + "\n" + loginAccount + "\n" + name);
            return true;
        }
        register3rd(loginFrom, loginAccount, name, ctx);
        return false;
    }
}
