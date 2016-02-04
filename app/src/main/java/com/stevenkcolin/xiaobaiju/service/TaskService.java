package com.stevenkcolin.xiaobaiju.service;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.dao.TaskDao;
import com.stevenkcolin.xiaobaiju.model.Task;
import com.stevenkcolin.xiaobaiju.model.User;
import com.stevenkcolin.xiaobaiju.util.DateUtil;
import com.stevenkcolin.xiaobaiju.util.FileUtil;
import com.stevenkcolin.xiaobaiju.util.HttpUtil;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.Date;
import java.util.List;

/**
 * Created by Pengfei on 2016/1/14.
 */
public class TaskService {
    public boolean uploadTask(Context ctx) throws Exception {
        List<String> list = FileUtil.read(ctx, GeneralConstant.FILE_NAME_LAST_SYNC);
        Date lastSyncTime = null;
        Date currentDate = new Date();
        if (list != null && !list.isEmpty()) {
            lastSyncTime = new Date(Long.valueOf(list.get(0)));
        }
        List<Task> taskList = TaskDao.getUnsyncTasks(lastSyncTime);
        if (taskList.isEmpty()) {
            return false;
        }

        for (Task task : taskList) {
            task.setUser(User.getUser().getId());
        }

//        String json = JSONUtil.stringifyArray(taskList);
        Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
        String json = gson.toJson(taskList);
        Log.e("report",GeneralConstant.SERVER_URL + GeneralConstant.TASK_URI + GeneralConstant.TASK_MASSCREATE_URI);


        JSONArray jsonArray = (JSONArray)HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.TASK_URI + GeneralConstant.TASK_MASSCREATE_URI, "POST", json);
        for (int i = 0; i < jsonArray.length(); i++) {
            Task task = taskList.get(i);
            if (task.get_id() == null) {
                task.set_id(jsonArray.getJSONObject(i).getString("_id"));
                TaskDao.save(task);
            }
            if (task.isDeleted()) {
                TaskDao.delete(task);
            }
        }

        FileUtil.write(ctx, GeneralConstant.FILE_NAME_LAST_SYNC, currentDate.getTime() + "");
        return true;
    }

    public boolean syncTask() throws Exception {
        JSONArray jsonArray = (JSONArray)HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.TASK_URI + GeneralConstant.TASK_FIND_URI + "?user=" + User.getUser().getId(), "GET", null);
        List<Task> taskList = TaskDao.getSyncTasks();
        for (Task task : taskList) {
            task.delete();
        }
        for (int i = 0; i < jsonArray.length(); i++) {
            JSONObject taskObject = jsonArray.getJSONObject(i);
            Task task = new Task();
            task.set_id(taskObject.getString("_id"));
            task.setCompleted(taskObject.getBoolean("completed"));
            task.setTitle((taskObject.getString("title")));
            task.setDueDate(DateUtil.toDate(taskObject.getString("dueDate")));
            task.setCreateDate(DateUtil.toDate(taskObject.getString("createDate")));
            TaskDao.save(task);
        }
        return true;
    }

}
