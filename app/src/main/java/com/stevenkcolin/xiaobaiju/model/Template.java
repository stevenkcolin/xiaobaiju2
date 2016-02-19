package com.stevenkcolin.xiaobaiju.model;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Pengfei on 2016/2/3.
 */
public class Template implements Serializable {
    private String name;
    private String _id;
    private List<ActionType> actionTypeList;

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

    public List<ActionType> getActionTypeList() {
        return actionTypeList;
    }

    public void setActionTypeList(List<ActionType> actionTypeList) {
        this.actionTypeList = actionTypeList;
    }
}
