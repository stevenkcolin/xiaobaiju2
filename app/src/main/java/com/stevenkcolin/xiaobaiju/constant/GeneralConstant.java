package com.stevenkcolin.xiaobaiju.constant;

/**
 * Created by Pengfei on 2015/12/9.
 */
public class GeneralConstant {
    public final static String SERVER = "www.stevenkcolin.com";
//    public final static String SERVER = "192.168.1.107";
    public final static String PORT = "3000";
    public final static String ROOT_URL = "api";
    public final static String SERVER_URL = "http://" + SERVER + ":" + PORT + "/" + ROOT_URL + "/";
    public final static int CONN_TIMEOUT = 6000;
    public final static int READ_TIMEOUT = 6000;

    public final static String USER_URI = "user/";
    public final static String USER_FIND_URI = "find";
    public final static String USER_CREATE_URI = "create";
    public final static String USER_LOGIN_URI = "login";
    public final static String USER_LOGIN_OTHER_URI = "loginByOther";

    public final static String TASK_URI = "task/";
    public final static String TASK_MASSCREATE_URI = "massUpdate";
    public final static String TASK_FIND_URI = "find";

    public final static String REPORT_URI = "reportInfo/";
    public final static String REPORT_CREATE_URI = "massCreate";

    public final static String TEMPLATE_URI = "template/";
    public final static String TEMPLATE_PUBLISH_URI = "published";
    public final static String TEMPLATE_DETAIL_URI = "detail";

    public final static String ATTACHMENT_URI = "attachment/";
    public final static String ATTACHMENT_DOWNLOAD_URI = "download";

    public final static String FILE_NAME_ACCOUNT = "account";
    public final static String FILE_NAME_LAST_SYNC = "lastSyncTime";

    //action page
    public final static int POST_ACTION_SIZE = 3;

    //3rd party login
    public final static String QQ = "QQ";

    public final static int SYNC_REPORT_COUNT = 10; //每到**个report into就上传


    public static final String SHARE_PREF = "SHARE_PREF_CUSTOM";
    public static final String SHARE_PREF_DOCTOR = "doctor_preference";
}
