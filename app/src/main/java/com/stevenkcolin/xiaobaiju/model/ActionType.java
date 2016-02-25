package com.stevenkcolin.xiaobaiju.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Pengfei on 2016/2/19.
 */
public class ActionType implements Serializable {
    private String name;
    private String _id;
    private List<PostAction> postActionList;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public List<PostAction> getPostActionList() {
        return postActionList;
    }

    public void setPostActionList(List<PostAction> postActionList) {
        this.postActionList = postActionList;
    }
}
