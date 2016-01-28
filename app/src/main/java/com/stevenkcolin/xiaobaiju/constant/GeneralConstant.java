package com.stevenkcolin.xiaobaiju.constant;

/**
 * Created by Pengfei on 2015/12/9.
 */
public class GeneralConstant {
//    public final static String SERVER = "www.stevenkcolin.com";
    public final static String SERVER = "192.168.1.108";
    public final static String PORT = "3000";
    public final static String SERVER_URL = "http://" + SERVER + ":" + PORT + "/";
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

    public final static String FILE_NAME_ACCOUNT = "account";
    public final static String FILE_NAME_LAST_SYNC = "lastSyncTime";

    //3rd party login
    public final static String QQ = "QQ";

    public final static int SYNC_REPORT_COUNT = 10; //每到**个report into就上传


    public static final String SHARE_PREF = "SHARE_PREF_CUSTOM";
    public static final String SHARE_PREF_DOCTOR = "doctor_preference";
}
