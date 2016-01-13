package com.stevenkcolin.xiaobaiju.report;

/**
 * Created by linchen on 1/13/16.
 */
public class ActionInfo {

    public int action_code;
    public String param1;
    public String param2;
    public String param3;
    public ActionInfo(){}
    public ActionInfo(int action_code, String param1, String param2,
                      String param3) {
        super();
        this.action_code = action_code;
        this.param1 = param1;
        this.param2 = param2;
        this.param3 = param3;
    }

    public ActionInfo(int action_code){
        super();
        this.action_code = action_code;
    }

    public ActionInfo(int action_code, String param1) {
        super();
        this.action_code = action_code;
        this.param1 = param1;
    }
    public ActionInfo(int action_code, String param1, String param2) {
        super();
        this.action_code = action_code;
        this.param1 = param1;
        this.param2 = param2;
    }
}
