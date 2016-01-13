package com.stevenkcolin.xiaobaiju.report;

/**
 * Created by linchen on 1/13/16.
 */
public class ActionRequestInfo {

    public String path;//请求的地址
    public String reqMethod;//POST|PUT|GET|DELETE 全大写
    public String reqTime;//操作发生时间，精确到秒
    public String retCode;//返回值，不需要详细返回信息  0，220，403
    public String reqDuration;//请求时长，单位毫秒
    public String httpStatus;//网络状态

    public ActionRequestInfo() {
    }

    public ActionRequestInfo(String path, String reqMethod, String reqTime, String retCode, String reqDuration, String httpStatus) {
        super();
        this.path = path;
        this.reqMethod = reqMethod;
        this.reqTime = reqTime;
        this.retCode = retCode;
        this.reqDuration = reqDuration;
        this.httpStatus = httpStatus;//网络状态
    }
}
