package com.stevenkcolin.xiaobaiju.vo;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class User {
    private static User user;

    private String id;
    private String phone;

    private User(){
        super();
    }

    public static User getUser(){
        if (user != null) {
            return user;
        }
        user = new User();
        return user;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
