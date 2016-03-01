package com.stevenkcolin.xiaobaiju.dao;

import com.stevenkcolin.xiaobaiju.model.PostAction;

import java.util.List;

/**
 * Created by Pengfei on 2015/12/23.
 */
public class PostActionDao {
    public static void save(PostAction postAction) {
        postAction.save();
    }

    public static void delete(PostAction postAction) {
        postAction.delete();
    }

    public static List<PostAction> getTaskList() {
        return PostAction.findWithQuery(PostAction.class,"select * from PostAction");
    }

    public static PostAction findById(Long id) {
        return PostAction.findById(PostAction.class, id);
    }
}

