package com.stevenkcolin.xiaobaiju.dao;

import com.stevenkcolin.xiaobaiju.vo.Task;

import java.util.List;

/**
 * Created by Pengfei on 2015/12/23.
 */
public class TaskDao {
    public static void save(Task task) {
        task.save();
    }

    public static List<Task> getTaskList() {
        return Task.findWithQuery(Task.class, "select * from Task order by completed desc, due_date desc");
    }
}
