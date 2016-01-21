package com.stevenkcolin.xiaobaiju.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;
import java.util.Date;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class Task extends SugarRecord implements Serializable {
    @SerializedName("localId")
    private Long id;
    @Expose
    private String _id;
    @Expose
    private String title;
    @Expose
    private String descrption;
    @Expose
    private Date dueDate;
    @Expose
    private boolean completed;
    @Expose
    private String user;
    @Expose
    private Date createDate;

    private Date lastUpdateTime;
    @Expose
    private boolean isDeleted;

    public Task() {
        super();
    }

    public Task(String title, String descrption, Date dueDate, boolean completed, String _id) {
        this.title = title;
        this.descrption = descrption;
        this.dueDate = dueDate;
        this.completed = completed;
        this._id = _id;
    }

    public Task(String title, String descrption)
    {
        this.title = title;
        this.descrption = descrption;
    }

    @Override
    public Long getId() {
        return id;
    }

    @Override
    public void setId(Long id) {
        this.id = id;
    }

    public String get_id() {
        return _id;
    }

    public void set_id(String _id) {
        this._id = _id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescrption() {
        return descrption;
    }

    public void setDescrption(String descrption) {
        this.descrption = descrption;
    }

    public Date getDueDate() {
        return dueDate;
    }

    public void setDueDate(Date dueDate) {
        this.dueDate = dueDate;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public Date getLastUpdateTime() {
        return lastUpdateTime;
    }

    public void setLastUpdateTime(Date lastUpdateTime) {
        this.lastUpdateTime = lastUpdateTime;
    }

    public boolean isDeleted() {
        return isDeleted;
    }

    public void setIsDeleted(boolean isDeleted) {
        this.isDeleted = isDeleted;
    }
}
