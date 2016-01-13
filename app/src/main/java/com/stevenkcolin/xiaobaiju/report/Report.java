package com.stevenkcolin.xiaobaiju.report;

import android.content.Context;
import android.util.Log;


/**
 * Created by Ken on 15/4/8.
 */
public class Report {


    private static Report mReport;

    private Report() {

    }

    public static Report getInstance() {
        if (mReport == null) {
            mReport = new Report();
        }
        return mReport;
    }

    public void saveOnClick(Context context, ActionInfo actionInfo) {
        if (actionInfo == null || actionInfo.action_code < 1){
            return;
        }
        ReportInfo report = new ReportInfo();
        //给report赋值
        report.actionCode = actionInfo.action_code;
        report.actionParam1 = actionInfo.param1;
        report.actionParam2 = actionInfo.param2;
        report.actionParam3 = actionInfo.param3;
        //接口调用数据上报
        String reportLog = new StringBuffer().append("\nactionCode=======").append(report.actionCode).append("\nactionParam1=======").append(report.actionParam1).append("\nactionParam2=======").append(report.actionParam2).append("\nactionParam3=======").append(report.actionParam3).toString();
        Log.e("report", reportLog);
        String client = "patient"; //CommonUtility.UIUtility.getClientName(context);
        if (client.contains("patient")) {
            report.appType = 1;
        } else {
            report.appType = 0;
        }
        report.appVer = ""; //CommonUtility.UIUtility.getVersionName(context);
//        CommonUtility.DebugLog.e("getIMEI",CommonUtility.DeviceInfoUtility.getIMEI(context));
//        CommonUtility.DebugLog.e("onlyid",CommonUtility.DeviceInfoUtility.getOnlyID(context));
//        report.localId = CommonUtility.getUUID(context);
        report.localId = ""; //CommonUtility.DeviceInfoUtility.getOnlyID(context);
//        CommonUtility.DebugLog.e("MD5",report.localId);
        report.ip = ""; //CommonUtility.getLocalHostIp();
        report.deviceModel = ""; //CommonUtility.getDeviceModel();
        report.os = ""; //CommonUtility.getOSVersion();
        report.actionTime = System.currentTimeMillis();
        report.netType = ""; //CommonUtility.NetTypeUtility.getNetType(context);
        report.carrier = ""; //CommonUtility.NetTypeUtility.getCarrier(context);
        report.channelId = ""; //CommonUtility.getChannelId(context);
        mAddSaveOnClickListener.getReport(report);
    }

    public void saveOnClick(Context context, ActionRequestInfo actionRequestInfo) {
        if (actionRequestInfo == null){
            return;
        }
        RequestReportInfo requestReportInfo = new RequestReportInfo();
        //接口调用数据上报
        requestReportInfo.path = actionRequestInfo.path;
        requestReportInfo.reqMethod = actionRequestInfo.reqMethod;
        requestReportInfo.reqTime = actionRequestInfo.reqTime;
        requestReportInfo.retCode = actionRequestInfo.retCode;
        requestReportInfo.reqDuration = actionRequestInfo.reqDuration;
        requestReportInfo.httpStatus = actionRequestInfo.httpStatus;
//        String reportLog = new StringBuffer().append("\npath=======").append(requestReportInfo.path).append("\nreqDuration=======").append(requestReportInfo.reqDuration).append("\nreqMethod=======").append(requestReportInfo.reqMethod).append("\nreqTime=======").append(requestReportInfo.reqTime).append("\nretCode=======").append(requestReportInfo.retCode).toString();
//        CommonUtility.DebugLog.e("requestReport",reportLog);
        String client = "patient"; //CommonUtility.UIUtility.getClientName(context);
        if (client.contains("patient")) {
            requestReportInfo.appType = 1;
        } else {
            requestReportInfo.appType = 0;
        }
        requestReportInfo.appVer = ""; //CommonUtility.UIUtility.getVersionName(context);
//        requestReportInfo.localId = CommonUtility.getUUID(context);
        requestReportInfo.localId = ""; //CommonUtility.DeviceInfoUtility.getOnlyID(context);
        requestReportInfo.ip = ""; //CommonUtility.getLocalHostIp();
        requestReportInfo.deviceModel = ""; //CommonUtility.getDeviceModel();
        requestReportInfo.os = ""; //CommonUtility.getOSVersion();
        requestReportInfo.netType = ""; //CommonUtility.NetTypeUtility.getNetType(context);
        requestReportInfo.carrier = ""; //CommonUtility.NetTypeUtility.getCarrier(context);
        requestReportInfo.channelId = ""; //CommonUtility.getChannelId(context);
        mAddSaveOnClickListener.getRequestReport(requestReportInfo);
    }

    /**
     * 有新的report
     */
    public interface AddSaveOnClickListener {
        void getReport(ReportInfo report);
        void getRequestReport(RequestReportInfo requestReportInfo);

    }

    private AddSaveOnClickListener mAddSaveOnClickListener;

    public void setAddSaveOnClickListener(AddSaveOnClickListener listener) {
        mAddSaveOnClickListener = listener;
    }


}
