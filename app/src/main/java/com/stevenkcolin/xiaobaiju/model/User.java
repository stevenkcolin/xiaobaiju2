package com.stevenkcolin.xiaobaiju.model;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class User {
    private static User user;

    private String id;
    private String phone;
    private String loginFrom;
    private String loginAccount;
    private String name;

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

    public String getLoginFrom() {
        return loginFrom;
    }

    public void setLoginFrom(String loginFrom) {
        this.loginFrom = loginFrom;
    }

    public String getLoginAccount() {
        return loginAccount;
    }

    public void setLoginAccount(String loginAccount) {
        this.loginAccount = loginAccount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
