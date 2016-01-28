package com.stevenkcolin.xiaobaiju.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.orm.SugarRecord;

import java.io.Serializable;

/**
 * Created by Ken on 15/4/8.
 */
public class ReportInfo extends SugarRecord implements Serializable {
    public int _id;//server id
    @SerializedName("_localId")
    public int id;//本地id
    @Expose
    public String userId;// 用户id，匿名用户上报0
    @Expose
    public String localId;// 用户本地id，表示唯一用户
    @Expose
    public int appType;// 0医生端／1糖友端
    @Expose
    public String appVer;// app版本
    @Expose
    public String ip;// 当前ip
    @Expose
    public String channelId;// 当前app渠道号
    @Expose
    public String deviceType = "android";// ios/android/web
    @Expose
    public String deviceModel;// iPhone6/HUAWEI
    @Expose
    public String netType;// 网络类型wifi/2g/3g/4g
    @Expose
    public String carrier;// 网络运营商
    @Expose
    public String os;// //操作系统
    @Expose
    public long actionTime;// 操作发生时间，精确到毫秒
    @Expose
    public int actionCode;// 预定义操作类型，数字，具体见action表
    @Expose
    public String actionParam1;// 操作参数，见action表
    @Expose
    public String actionParam2;// 操作参数，见action表
    @Expose
    public String actionParam3;// 操作参数，见action表
    @Expose
    public String longitude;// 经度，6位小数
    @Expose
    public String latitude;// 纬度，6位小数
}
