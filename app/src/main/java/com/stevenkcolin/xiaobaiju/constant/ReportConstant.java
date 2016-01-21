package com.stevenkcolin.xiaobaiju.constant;

/**
 * Created by Pengfei on 2016/1/21.
 */
public class ReportConstant {
    //ActionCode
    //所有页面中的上方ActionBar 001;

    //侧滑菜单中的按钮，包括Login & AboutUs
    //侧滑菜单的pageId=100
    //taskList的pageId=101
    //taskDetail的pageId=102;

    //侧滑菜单中的按钮
    public static final int REPORT_MENU_LOGIN = 100001001;
    public static final int REPORT_MENU_ABOUTUS = 100001002;
    //tasklist中用户操作侧滑菜单操作。侧滑菜单的moduleId=011
    public static final int REPORT_TASKLIST_MENUSHOW = 101011001;
    public static final int REPORT_TASKLIST_MENUHIDE = 101011002;
    //taskList中上方actionbar的按钮，actionBar的moduleId一律001
    public static final int REPORT_TASKLIST_SETTINGS = 101001001;
    public static final int REPORT_TASKLIST_REFRESH = 101001002;
    //taskList中中间的菜单按钮，page的moduleId=002;
    public static final int REPORT_TASKLIST_CHECKTASK = 101002001;
    public static final int REPORT_TASKLIST_UNCHECKTASK = 101002002;
    public static final int REPORT_TASKLIST_ADDTASK = 101002003;
    public static final int REPORT_TASKLIST_EDITTASK = 101002004;

    //taskDetail中侧滑菜单操作，侧滑菜单的moduleId=011;
    public static final int REPORT_TASKDETAIL_MENUSHOW = 102011001;
    public static final int REPORT_TASKDETAIL_MENUHIDE = 102011002;
    //taskDetail中上方actionBar的按钮， actionBar的moduelId=001
    public static final int REPORT_TASKDETAIL_SETTINGS = 102001001;
    public static final int REPORT_TASKDETAIL_DELETE = 102001002;
    public static final int REPORT_TASKDETAIL_BACK = 102001003;
    //taskDetail中中间的菜单按钮，page的moduleId=002;
    public static final int REPORT_TASKDETAIL_CHECKTASK = 102002001;
    public static final int REPORT_TASKDETAIL_UNCHECKTASK = 102002002;
    public static final int REPORT_TASKDETAIL_EDITDATE = 102002003;

//        //ActionCode
//        public static final int REPORT_REQUEST = 0;
//
//        public static final int REPORT_ADD_CLICK = 1;
//        public static final int REPORT_ADD_BLOOD_RECORD = 2;
//        public static final int REPORT_ADD_MEAL_RECORD = 3;
//        public static final int REPORT_ADD_SPORTS_RECORD = 4;
//        public static final int REPORT_ADD_DRUG_RECORD = 5;
//        public static final int REPORT_ADD_BLOOD_PRESURE_RECORD = 6;
//        public static final int REPORT_ADD_CHEC_RECORD = 7;
//        public static final int REPORT_ADD_HBAC1_RECORD = 8;
//        public static final int REPORT_RECORD_PAGE_CLICK = 9;
//        public static final int REPORT_DOCTOR_GUIDE = 10;
//        public static final int REPORT_DOCTOR_LIST_CLICK = 11;
//        public static final int REPORT_MONTH_SERVICE_CLICK = 12;
//        public static final int REPORT_READ_ARTICLE = 13;
//        public static final int REPORT_LOGIN_GUIDE = 14;
//        public static final int REPORT_USER_REGISTER = 15;
//        public static final int REPORT_FOOD_LIB = 16;
//        public static final int REPORT_MEAL_TIPS = 17;
//        public static final int REPORT_PROFILE_MODIFY = 18;
//        public static final int REPORT_APP_START = 19;
//        public static final int REPORT_DELETE_BLOOD_RECORD = 20;
//        public static final int REPORT_DELETE_MEAL_RECORD = 21;
//        public static final int REPORT_DELETE_SPORTS_RECORD = 22;
//        public static final int REPORT_DELETE_DRUG_RECORD = 23;
//        public static final int REPORT_DELETE_BLOOD_PRESURE_RECORD = 24;
//        public static final int REPORT_DELETE_CHEC_RECORD = 25;
//        public static final int REPORT_DELETE_HBAC1_RECORD = 26;
//        public static final int REPORT_MODIFY_BLOOD_RECORD = 27;
//        public static final int REPORT_MODIFY_MEAL_RECORD = 28;
//        public static final int REPORT_MODIFY_SPORTS_RECORD = 29;
//        public static final int REPORT_MODIFY_DRUG_RECORD = 30;
//        public static final int REPORT_MODIFY_BLOOD_PRESURE_RECORD = 31;
//        public static final int REPORT_MODIFY_CHEC_RECORD = 32;
//        public static final int REPORT_MODIFY_HBAC1_RECORD = 33;
//        public static final int REPORT_SERVICE_ENTRY = 34;
//        public static final int REPORT_SERVICE_EXCHANGE = 35;
//        public static final int REPORT_TARGET_EVALUATE = 36;
//        public static final int REPORT_HBA1C_ENTRY = 37;
//        public static final int REPORT_DOC_MATCH_VIEW = 38;
//        public static final int REPORT_NEW_USER_FIRST = 39;
//        public static final int REPORT_DONUTS_BANNER = 40;
//        public static final int REPORT_DONUTS_RECOMM = 41;
//        public static final int REPORT_ARTICLE = 42;
//        public static final int REPORT_CATOGERY = 43;
//        public static final int REPORT_PUSH_WAKE = 44;
//        public static final int REPORT_BLOOD_SUGER_MONITORING = 45;
//        public static final int REPORT_MANAGEMENT_SCHEME = 46;
//        public static final int REPORT_PATIENT_RESTAURANT = 47;
//        public static final int REPORT_LUCKY_DRAW = 48;
//        public static final int REPORT_LUCKY_DRAW_BEGIN = 49;
//        public static final int REPORT_DOC_HOMEPAGE = 50;
//        public static final int REPORT_NEW_MESSAGE = 51;
//        //糖友端2.7新增
//        public static final int REPORT_SHARE = 52;
//        public static final int REPORT_PORTABLE_TOOL = 53;
//        public static final int REPORT_GLUCOSE_DATA = 54;
//        public static final int REPORT_GLUCOSE_ANALYSIS = 55;
//        public static final int REPORT_FOOD_LIB_SEARCH = 56;
//        //糖友端3.0
//        public static final int REPORT_TIP_RECORD_BLOOD_SUGAR = 57;
//        public static final int REPORT_ORDER = 58;
//        public static final int REPORT_COUPONS = 59;
//        public static final int REPORT_CHINA_GOOD_DOCTOR = 60;
//        public static final int REPORT_EVERYONE_IS_ASKING = 61;
//        public static final int REPORT_DOCTOR_HOME_SERVICE = 62;
//        public static final int REPORT_BLOOD_SUGAR_REQUIRED_COURSE = 63;
//        public static final int REPORT_CHAT_FORM_SERVICE = 64;
//        public static final int REPORT_BUY_NOW = 65;
//        public static final int REPORT_PAY_NOW = 66;
//        public static final int REPORT_ADD_PHONE = 67;
//
//        //医生端行为上报
//        public static final int REPORT_HOME_TAB = 1001;
//        public static final int REPORT_PATIENT_TAB = 1002;
//        public static final int REPORT_MY_TAB = 1003;
//        public static final int REPORT_BREAKFAST78_TAB = 1004;
//        public static final int REPORT_PATIENT_PROFILE = 1005;
//        public static final int REPORT_CHAT = 1006;
//        public static final int REPORT_ADD_PATIENT = 1007;
//        public static final int REPORT_ALL_BLOOD_SUGAR_DATA = 1008;
//        public static final int REPORT_MANAGE_TARGET = 1009;
//        public static final int REPORT_BLOOD_SUGAR_TARGET = 1010;
//        public static final int REPORT_DOCTOR_INFO = 1011;
//        public static final int REPORT_DOCTOR_AUTH = 1012;
//        public static final int REPORT_OPEN_SERVICE = 1013;
//        public static final int REPORT_NOTIFICATION = 1014;
//        public static final int REPORT_SERVICE_SETTING = 1015;
//        public static final int REPORT_START = 1016;
//        public static final int REPORT_PUSH = 1017;
//        //医生端3.0新增
//        public static final int REPORT_COMPETING_PRODUCTS = 1018;
//        public static final int REPORT_WORKBENCH = 1019;
//        public static final int REPORT_GALLERY = 1020;
//        public static final int REPORT_CERTIFIED_DOCTORS = 1021;
//        public static final int REPORT_ATTENDANCE = 1022;
//        public static final int REPORT_RECOMMEND = 1023;
//        public static final int REPORT_LOVING_DOCTOR = 1024;
//        public static final int REPORT_MY_PATIENT = 1025;
//        public static final int REPORT_MY_INCOME = 1026;
//        public static final int REPORT_FLOWER = 1027;
//        public static final int REPORT_CARD = 1028;
//        public static final int REPORT_INVITE_PATIENT = 1029;
//        public static final int REPORT_MORE = 1030;
//        public static final int REPORT_KNOWLEDGE = 1031;
//        public static final int REPORT_FOOD = 1032;
//        public static final int REPORT_CALCULATE = 1033;
//        public static final int REPORT_BANNER_1 = 1034;
//        public static final int REPORT_BANNER_2 = 1035;
//        public static final int REPORT_BANNER_3 = 1036;
//
//
//        //血糖
//        public static final String BEFORE_BREAKFAST = "beforeBreakfast";
//        public static final String AFTER_BREAKFAST = "afterBreakfast";
//        public static final String BEFORE_LUNCH = "beforeLunch";
//        public static final String AFTER_LUNCH = "afterLunch";
//        public static final String BEFORE_DINNER = "beforeDinner";
//        public static final String AFTER_DINNER = "afterDinner";
//        public static final String BEFORE_SLEEP = "beforeSleep";
//        public static final String RANDOM_REC = "randomRec";
//        public static final String AM3_STUB = "am3Stub";
//
//        public static final String MANUAL = "manual";
//        public static final String YUYUE780BLUE = "yuyue780blue";
//        //饮食
//        public static final String BREAKFAST = "breakfast";
//        public static final String LUNCH = "lunch";
//        public static final String DINNER = "dinner";
//        public static final String ADDITION = "addition";
//        //运动
//        public final static String PEDOMETER0 = "pedometer0";
//        public final static String PEDOMETER = "pedometer";
//        //用药
//        public static final String INSULIN = "insulin";
//        public static final String SUGARLOWER = "sugarlower";
//        public static final String PRESSRELOWER = "pressrelower";
//        public static final String LIPIDLOWER = "lipidlower";
//        //控糖页面点击
//        public static final String ADDRECORD = "addrecord";
//        public static final String ANALYSIS = "analysis";
//        public static final String FOOD = "food";
//        public static final String KNOWLEDGE = "knowledge";
//        public static final String SPORT = "sport";
//        public static final String DRUG = "drug";
//        public static final String ELSE = "else";
//        public static final String GLUCOSE = "glucose";
//        //医生咨询引导or医生列表
//        public static final String RECORDBLOOD = "recordblood";
//        public static final String MEAL = "meal";
//        public static final String KNOWLEDGE_RESEARCH = "knowledge_research";
//        public static final String DOCTOR = "doctor";
//        public static final String DIETITIAN = "dietitian";
//        public static final String TEAM_BYB = "team_byb";
//        //登录引导
////        public static final String DOCTOR = "doctor";
//        public static final String RECORD = "record";
//        public static final String PROFILE = "profile";
//        public static final String SYNCDATA = "syncdata";
//        public static final String ARTICLE = "article";
//        //食物库
//        public static final String SEARCH = "search";
//        public static final String LIST = "list";
//        //修改档案
//        public static final String TARGET = "target";
//        public static final String DIABETES = "diabetes";
//        public static final String DRUGPLAN = "drugplan";
//        //服务入口
//        public static final String SINGLE = "single";
//        public static final String MANAGER = "manager";
//
//        public static final String DOC_NORMAL = "doc_normal";
//        public static final String DOC_MATCH = "doc_match";
//        public static final String DOC_VIP = "doc_vip";
//        //服务兑换
//        public static final String SINGLE_QUESTION = "single_question";
//        public static final String SINGLE_TARGET = "single_target";
//        public static final String MANAGER_15 = "manager_15";
//        public static final String MANAGER_30 = "manager_30";
//        //目标评估
//        public static final String TARGET_ENTRY = "target_entry";
//        public static final String TARGET_AUTO_START = "target_auto_start";
//        public static final String TARGET_AUTO_DONE = "target_auto_done";
//        public static final String TARGET_SET = "target_set";
//        public static final String TARGET_ASKDOC = "target_askdoc";
//        //糖化检测
//        public static final String HBA1C_ENTRY = "hba1c_entry";
//        public static final String HBA1C_BIND = "hba1c_bind";
//        public static final String HBA1C_RESULT_VIEW = "hba1c_result_view";
//        //新手引导
//        public static final String START_CLICK = "start_click";
//        public static final String LOGIN_CLICK = "login_click";
//        public static final String P1_NEXT = "p1_next";
//        public static final String P2_NEXT = "p2_next";
//        public static final String P3_NEXT = "p3_next";
//        public static final String P4_NEXT = "p4_next";
//        public static final String P5_NEXT = "p5_next";
//        public static final String P6_NEXT = "p6_next";
//        public static final String P7_NEXT = "p7_next";
//        public static final String[] NEXT = {P1_NEXT, P2_NEXT, P3_NEXT, P4_NEXT, P5_NEXT, P6_NEXT, P7_NEXT};
//        public static final String REG_CLICK = "reg_click";
//        //推送
//        public static final String MESSAGE = "message";
//        public static final String DOC_SEND_ARTICLE = "doc_send_article";
//        public static final String SYS_MESSAGE = "sys_message";
//        public static final String OP_ARTICLE = "op_article";
//        public static final String SYS_URL = "sys_url";
//        public static final String WECHAT_SCAN = "wechat_scan";
//        public static final String STEP_REMIND = "step_remind";
//        public static final String DRUG_REMIND = "drug_remind";
//        //血糖监测方案
//        public static final String MONITORING_ENTRY = "monitoring_entry";
//        public static final String MONITORING_RESULTS = "monitoring_results";
//        public static final String MONITORING_START = "monitoring_start";
//        public static final String MONITORING_DONE = "monitoring_done";
//        public static final String MONITORING_SET = "monitoring_set";
//
//        public static final String MANAGEMENT_SCHEME_ENTRY = "management_scheme_entry";
//        public static final String MANAGEMENT_STYLE_ENTRY = "management_style_entry";
//        public static final String ADD_DRUG_RECORD = "add_drug_record";
//
//        public static final String PATIENT_RESTAURANT_ENTRY = "patient_restaurant_entry";
//
//        //基本资料页
//        public static final String ONLINE2 = "Online2";
//        public static final String ONLINE1 = "Online1";
//        public static final String PICTURE = "Picture";
//        //认证页面
//        public static final String BANNER = "Banner";
//        public static final String HOMEPAGE = "Homepage";
//        public static final String APPLICATION = "Application";
//        public static final String NOTIFICATION = "Notification";
//
//        //从医患对话页面点击
//        public static final String DIALOGUE = "dialogue";
//
//        //二维码（工作台）⬇⬇医生端3.0新增⬇⬇
//        public static final String CARD_WORKBENCH = "Card_workbench";
//        //二维码（我）
//        public static final String CARD_MY = "Card_my";
//        //邀请患者（工作台）
//        public static final String INVITE_WORKBENCH = "Invite_Workbench";
//        //邀请患者（接诊页）
//        public static final String INVITE_ADMISSIONS = "Invite_Admissions";
//        //邀请患者（爱心医生）
//        public static final String INVITE_LOVING_DOCTOR = "Invite_loving_doctor";
//        //随身宝典点击  ⬇⬇糖友端2.7新增⬇⬇
//        public static final String PORTABLE_TOOL = "portable_tool";
//        //趣味控糖点击
//        public static final String RECOMMEND_ = "recommend_";
//        //随身宝典--新糖友指南
//        public static final String GUIDE = "guide";
//        //随身宝典--食物库
//        public static final String FOOD_LIB = "food_lib";
//
//        //血糖数据-图表点击
//        public static final String CHART = "chart";
//        //血糖数据-分析点击
////        public static final String ANALYSIS = "analysis";
//        //血糖数据-日志点击
////        public static final String RECORD = "record";
//        //血糖分析 -- 糖化血糖蛋白tips点击
//        public static final String TIPS = "tips";
//        //血糖分析 -- 当天点击
//        public static final String TODAY = "today";
//        //血糖分析 -- 7天点击
//        public static final String SEVEN_DAYS = "7days";
//        //血糖分析 -- 30天点击
//        public static final String THIRTY_DAYS = "30days";
//        //血糖分析 -- 全屏查看点击（3天、7天、30天加和）
//        public static final String FULLSCREEN = "fullscreen";
//        //食物库搜索页 -- 用户搜索词上报
//        public static final String SEARCHWORD = "searchword";
//        //食物库搜索页 -- 最近搜索点击
//        public static final String RECENT = "recent";
//        //食物库搜索页 -- 热搜词点击
//        public static final String HOT = "hot";
//        //食物库 -- banner点击
////        public static final String BANNER = "banner";
//        //食物库 -- 食物红绿灯点击
//        public static final String LIGHTS = "lights";
//        //食物库 -- 低血糖吃什么点击
//        public static final String HYPOGLYCEMIA = "hypoglycemia";
//        //食物库 -- 加餐吃什么点击
//        public static final String SNACK = "snack";
//        //食物库 -- 细数食物脂肪点击
//        public static final String FAT = "fat";
//
//        public static final String TIP_2_RECORD_BLOOD_SUGAR_PARAM = "entry";
//        public static final String AREA = "area";
//        public static final String SPECIALTY = "specialty";
//        public static final String ASK = "ask";
//        public static final String PHONE = "phone";
//        public static final String VIP = "vip";
//        public static final String SUGAR = "sugar";
//        public static final String SERVICE = "service";
//        public static final String ORDER = "order";
}
