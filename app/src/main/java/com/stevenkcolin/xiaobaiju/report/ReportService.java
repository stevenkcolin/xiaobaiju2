package com.stevenkcolin.xiaobaiju.report;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.model.ReportInfo;
import com.stevenkcolin.xiaobaiju.util.HttpUtil;

import java.util.List;

/**
 * Created by Pengfei on 2016/1/28.
 */
public class ReportService {
    /**
     * 上报数据
     */
    public void report(ReportInfo reportInfo) {
        reportInfo.save();
        uploadReport();
    }


    private void uploadReport() {
        long count = ReportInfo.count(ReportInfo.class, null, null);
        if (count < GeneralConstant.SYNC_REPORT_COUNT) {
            return;
        }
        Thread thread = new Thread(new Runnable() {
            @Override
            public void run() {
                List<ReportInfo> reportInfoList = ReportInfo.listAll(ReportInfo.class);
                if (reportInfoList != null && !reportInfoList.isEmpty()) {
                    Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
                    String json = gson.toJson(reportInfoList);
                    try {
                        HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.REPORT_URI + GeneralConstant.REPORT_CREATE_URI, "POST", json);
                        ReportInfo.deleteAll(ReportInfo.class);
                    } catch (Exception e) {
                        //do nothing. it can be synced next time
                    }
                }
            }
        });
        thread.start();
    }
}
