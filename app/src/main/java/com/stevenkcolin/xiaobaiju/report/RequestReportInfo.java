package com.stevenkcolin.xiaobaiju.report;

/**
 * Created by Ken on 15/4/8.
 */
public class RequestReportInfo {
    public int _id;
    public String userId;// 用户id，匿名用户上报0
    public String localId;// 用户本地id，表示唯一用户
    public int appType;// 0医生端／1糖友端
    public String appVer;// app版本
    public String ip;// 当前ip
    public String channelId;// 当前app渠道号
    public String deviceType = "android";// ios/android/web
    public String deviceModel;// iPhone6/HUAWEI
    public String netType;// 网络类型wifi/2g/3g/4g
    public String carrier;// 网络运营商
    public String os;// //操作系统
    public String longitude;// 经度，6位小数
    public String latitude;// 纬度，6位小数
    public String reqTime;//操作发生时间，精确到秒
    public String path;//请求的地址
    public String reqMethod;//POST|PUT|GET|DELETE 全大写
    public String retCode;//返回值，不需要详细返回信息  0，220，403
    public String reqDuration;//请求时长，单位毫秒
    public String httpStatus;//网络状态


}
