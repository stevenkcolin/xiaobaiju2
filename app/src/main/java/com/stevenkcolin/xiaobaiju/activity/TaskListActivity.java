package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.constant.ReportConstant;
import com.stevenkcolin.xiaobaiju.fragment.BaseFragment;
import com.stevenkcolin.xiaobaiju.fragment.TaskListFragment;
import com.stevenkcolin.xiaobaiju.report.ActionInfo;
import com.stevenkcolin.xiaobaiju.service.TaskService;
import com.stevenkcolin.xiaobaiju.service.UserService;
import com.stevenkcolin.xiaobaiju.util.DialogUtil;
import com.stevenkcolin.xiaobaiju.util.FileUtil;
import com.stevenkcolin.xiaobaiju.util.SyncUtil;
import com.tencent.bugly.crashreport.CrashReport;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import java.util.List;
import java.util.Map;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class TaskListActivity extends BaseActivity {

    private ProgressDialog mProgressDialog;

    private SlidingMenu mSlidingMenu;
    private UMShareAPI mShareAPI = null;

    private BaseFragment mFragment;
    private TaskListFragment mFragmentTask = new TaskListFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        CrashReport.initCrashReport(this, "900018308", false);
        setContentView(R.layout.activity_main);

        mFragment = mFragmentTask;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mFragment).commit();

        //添加左滑菜单
        addSlidingMenu();
        new LoginBGTask().execute();

        /*底部菜单事件*/
        RadioGroup rg = (RadioGroup) findViewById(R.id.tab_menu);
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.menu_task:
                        mFragment = mFragmentTask;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mFragment).commit();
                        changeMenuIcon(true, false, false);
                        break;
                    default:
                        break;
                }
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        ActionInfo mActionInfo;
        switch (item.getItemId()) {
            case R.id.settings:
                //add settings code
                //添加打点上报代码
                mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKLIST_SETTINGS);
                mReport.saveOnClick(getApplicationContext(),mActionInfo);
                return true;
            case R.id.refresh:
                mFragment.fresh();
                //添加打点上报代码
                mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKLIST_REFRESH);
                mReport.saveOnClick(getApplicationContext(),mActionInfo);
                return true;
            default:
        }
        return true;
    }


    //添加slidingMenu
    public void addSlidingMenu(){
        mSlidingMenu = new SlidingMenu(this);//创建对象
        mSlidingMenu.setMode(SlidingMenu.LEFT);//设定模式，SlidingMenu在左边
        mSlidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//配置slidingmenu偏移出来的尺寸
        mSlidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏都可以拖拽触摸，打开slidingmenu
        mSlidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//附加到当前的Aty上去
        mSlidingMenu.setMenu(R.layout.activity_xiaobaiju_sliding_menu);


        /** init auth api**/
        try {
            mShareAPI = UMShareAPI.get(this);
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        //微信    wx12342956d1cab4f9,a5ae111de7d9ea137e88a5e02c07c94d
        PlatformConfig.setWeixin("wx967daebe835fbeac", "5bb696d9ccd75a38c8a0bfe0675559b3");
        //豆瓣RENREN平台目前只能在服务器端配置
        //新浪微博
        PlatformConfig.setSinaWeibo("3921700954", "04b48b094faeb16683c32669824ebdad");
        //易信
        PlatformConfig.setYixin("yxc0614e80c9304c11b0391514d09f13bf");
        PlatformConfig.setQQZone("100424468", "c7394704798a158208a74ab60104f0ba");
        PlatformConfig.setTwitter("3aIN7fuF685MuZ7jtXkQxalyi", "MK6FEYG63eWcpDFgRYw4w9puJhzDl0tyuqWjZ3M7XJuuG7mMbO");
        PlatformConfig.setAlipay("2015111700822536");
        PlatformConfig.setLaiwang("laiwangd497e70d4", "d497e70d4c3e4efeab1381476bac4c5e");
        PlatformConfig.setPinterest("1439206");

        //set loginbutton to open Login Page
        openLoginPage();

        //set about_us button to have a toast
        openAboutUs();
    }

    //给左侧餐单按钮about_us添加事件，欢迎打开官方网站
    private void openAboutUs(){
        findViewById(R.id.about_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.txt_about_us,Toast.LENGTH_SHORT).show();
                //添加打点上报代码
                ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_ABOUTUS);
                mReport.saveOnClick(getApplicationContext(), mActionInfo);
                // TODO: 1/8/16 完成官方网站的web端，并打开官方网站。
            }
        });
    }

    //给左侧菜单按钮login_account添加事件，打开login Page
    private void openLoginPage(){
        findViewById(R.id.login_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                loginQQ(view);
            }
        });
    }
    //添加登录QQ的代码
    public void loginQQ(View view) {
        if (view.getId() == R.id.login_account) {
            SHARE_MEDIA platform = null;
            platform = SHARE_MEDIA.QQ;
            /**begin invoke umeng api**/
            try {
                mShareAPI.doOauthVerify(TaskListActivity.this, platform, umAuthListener);
                mShareAPI.getPlatformInfo(TaskListActivity.this,platform, umAuthListener);

                //添加打点上报代码
                ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_LOGIN);
                mReport.saveOnClick(getApplicationContext(),mActionInfo);
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }
    }

//    添加call back方法，专门用作umeng事件
    /** auth callback interface**/
    private UMAuthListener umAuthListener = new UMAuthListener() {
        @Override
        public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
            if (data != null && data.get("openid") != null){
                String openId = data.get("openid");
                String name = data.get("screen_name");
                new LoginTask().execute(new String[]{GeneralConstant.QQ, openId, name});
            }
        }

        @Override
        public void onError(SHARE_MEDIA platform, int action, Throwable t) {
            Toast.makeText( getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform, int action) {
            Toast.makeText( getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
        }
    };
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
    }

    //登录
    class LoginTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            mProgressDialog = DialogUtil.showWaitDialog(TaskListActivity.this, getString(R.string.please_wait));
        }

        @Override
        protected Boolean doInBackground(String... params) {
            String loginFrom = params[0];
            String loginAccount = params[1];
            String name = params[2];
            try {
                UserService userService = new UserService();
                userService.login3rdAcountExist(loginFrom, loginAccount, name, TaskListActivity.this);
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            if (aBoolean && !SyncUtil.isSync) {
                new SyncTask().execute();
                mProgressDialog.dismiss();
            } else {
                mProgressDialog.dismiss();
                Toast.makeText(getBaseContext(), getString(R.string.error_network), Toast.LENGTH_SHORT).show();
            }
        }
    }

    class SyncTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
            SyncUtil.isSync = true;
        }

        @Override
        protected Boolean doInBackground(String... params) {
            TaskService taskService = new TaskService();
            try {
                taskService.syncTask();
                return true;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
            SyncUtil.isSync = false;
            if (aBoolean) {
                mFragment.fresh();
            }
            mProgressDialog.dismiss();
        }
    }


    class LoginBGTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(String... params) {
            List<String> userInfo = FileUtil.read(TaskListActivity.this, GeneralConstant.FILE_NAME_ACCOUNT);
            boolean result = false;
            try {
                if (userInfo != null & !userInfo.isEmpty()) {
                    UserService userService = new UserService();
                    if (userInfo.get(0).equals("0")) {
                        result = userService.login(userInfo.get(1), userInfo.get(2), TaskListActivity.this);
                    } else if (userInfo.get(0).equals("1")) {
                        result = userService.login3rdAcountExist(userInfo.get(1), userInfo.get(2), userInfo.get(3), TaskListActivity.this);
                    }
                }
                return result;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean aBoolean) {
        }
    }

        private void changeMenuIcon(boolean main, boolean account, boolean more) {
            RadioButton radioMain = (RadioButton)findViewById(R.id.menu_task);
            RadioButton radioAccount = (RadioButton)findViewById(R.id.menu_action);
            RadioButton radioMore = (RadioButton)findViewById(R.id.menu_mine);
            radioMain.setChecked(main);
            radioAccount.setChecked(account);
            radioMore.setChecked(more);
        }

}
