package com.stevenkcolin.xiaobaiju.service;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.model.Template;
import com.stevenkcolin.xiaobaiju.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.List;

/**
 * Created by Pengfei on 2016/2/3.
 */
public class ActionService {
    public List<Template> getAllTemplate() throws Exception {
        JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.TEMPLATE_URI + GeneralConstant.TEMPLATE_PUBLISH_URI, "GET", null);
        Gson gson = new Gson();
        List<Template> templateList = gson.fromJson(jsonArray.toString(), new TypeToken<List<Template>>(){}.getType());
        return templateList;
    }

    public Template getTemplateDetail(String templateId) throws Exception {
        JSONObject jsonObject = (JSONObject) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.TEMPLATE_URI + GeneralConstant.TEMPLATE_DETAIL_URI
                + "?id=" + templateId + "&limit=" + GeneralConstant.POST_ACTION_SIZE, "GET", null);
        Gson gson = new Gson();
        Template template = gson.fromJson(jsonObject.toString(), Template.class);
        return template;
    }
}
