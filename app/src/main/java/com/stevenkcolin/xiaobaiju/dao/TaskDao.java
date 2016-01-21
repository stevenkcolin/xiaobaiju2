package com.stevenkcolin.xiaobaiju.dao;

import com.stevenkcolin.xiaobaiju.model.Task;

import java.util.Date;
import java.util.List;

/**
 * Created by Pengfei on 2015/12/23.
 */
public class TaskDao {
    public static void save(Task task) {
        if (task.getCreateDate() == null) {
            task.setCreateDate(new Date());
        }
        task.setLastUpdateTime(new Date());
        task.save();
    }

    public static void delete(Task task) {
        task.delete();
    }

    public static List<Task> getTaskList() {
        return Task.findWithQuery(Task.class, "select * from Task where is_deleted != '1' order by completed, due_date");
    }

    public static List<Task> getUnsyncTasks(Date lastUpdateTime) {
        String sql = "select * from Task";
        if (lastUpdateTime != null) {
            sql = "select * from Task where is_deleted = '1' or last_update_time >= '" + lastUpdateTime.getTime() + "'";
        }
        return Task.findWithQuery(Task.class, sql);
    }

    public static List<Task> getSyncTasks() {
        return Task.findWithQuery(Task.class, "select * from Task where _id != 'null'");
    }

    public static Task findById(Long id) {
        return Task.findById(Task.class, id);
    }

    public static void markAsDelete(Task task) {
        task.setIsDeleted(true);
        task.save();
    }
}
