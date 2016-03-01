package com.stevenkcolin.xiaobaiju.model;

import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Pengfei on 2016/2/24.
 */
public class PostAction extends SugarRecord implements Serializable {
    private String title;
    private String _id;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public PostAction (String title) {
        this.title = title;
    }

    public PostAction () {
        super();
    }
}
