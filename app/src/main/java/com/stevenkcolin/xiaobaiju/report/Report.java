package com.stevenkcolin.xiaobaiju.report;

import android.content.Context;
import android.util.Log;

import com.stevenkcolin.xiaobaiju.model.ReportInfo;
import com.stevenkcolin.xiaobaiju.model.User;
import com.stevenkcolin.xiaobaiju.util.CommonUtil;
import com.stevenkcolin.xiaobaiju.util.GeoUtil;


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
        if (User.getUser().getId()!=null) {
            report.userId = User.getUser().getId();
        }
        report.actionCode = actionInfo.action_code;
        report.actionParam1 = actionInfo.param1;
        report.actionParam2 = actionInfo.param2;
        report.actionParam3 = actionInfo.param3;
        //接口调用数据上报
        String reportLog = new StringBuffer().append("\nactionCode=======").append(report.actionCode).append("\nactionParam1=======").append(report.actionParam1).append("\nactionParam2=======").append(report.actionParam2).append("\nactionParam3=======").append(report.actionParam3).toString();
        Log.d("report", reportLog);

        report.appType = 1;
        report.appVer = CommonUtil.getVersionName(context);
        report.localId = CommonUtil.DeviceInfoUtility.getOnlyID(context);
        report.ip = CommonUtil.DeviceInfoUtility.getLocalHostIp(); //CommonUtility.getLocalHostIp();
        Log.d("report","appver:"+report.appVer+"-----------"+"localId:"+report.localId+"----------"+"ip:"+report.ip);

        report.deviceModel = CommonUtil.getDeviceModel(); //CommonUtility.getDeviceModel();
        report.os = CommonUtil.getOSVersion(); //CommonUtility.getOSVersion();
        report.actionTime = System.currentTimeMillis();
        Log.d("report","deviceModel:"+report.deviceModel+"--------"+"os:"+report.os+"---------"+"actionTime:"+report.actionTime);

        report.netType = CommonUtil.NetTypeUtility.getNetType(context); //CommonUtility.NetTypeUtility.getNetType(context);
        report.carrier = CommonUtil.NetTypeUtility.getCarrier(context); //CommonUtility.NetTypeUtility.getCarrier(context);
        report.channelId = CommonUtil.getChannelId(context); //CommonUtility.getChannelId(context);

        Double[] coordinate = GeoUtil.getCoordinate(context);
        if (coordinate!=null) {
            if (coordinate[0] != null) {
                report.latitude = coordinate[0] + "";
            }
            if (coordinate[1] != null) {
                report.longitude = coordinate[1] + "";
            }
        }
        Log.d("report", "netType:" + report.netType + "----------" + "carrier:" + report.carrier + "--------" + "channelId:" + report.channelId);

        ReportService reportService = new ReportService();
        reportService.report(report);
    }

    /*public void saveOnClick(Context context, ActionRequestInfo actionRequestInfo) {
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
    }*/

}
