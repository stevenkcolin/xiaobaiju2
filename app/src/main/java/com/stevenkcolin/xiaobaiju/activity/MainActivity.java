package com.stevenkcolin.xiaobaiju.activity;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.constant.ReportConstant;
import com.stevenkcolin.xiaobaiju.fragment.ActionListFragment;
import com.stevenkcolin.xiaobaiju.fragment.BaseFragment;
import com.stevenkcolin.xiaobaiju.fragment.MineFragment;
import com.stevenkcolin.xiaobaiju.fragment.TaskListFragment;
import com.stevenkcolin.xiaobaiju.report.ActionInfo;
import com.stevenkcolin.xiaobaiju.service.UserService;
import com.stevenkcolin.xiaobaiju.util.FileUtil;

import java.util.List;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class MainActivity extends BaseActivity {

    private BaseFragment mFragment;
    private TaskListFragment mFragmentTask = new TaskListFragment();
    private ActionListFragment mFragmentAction = new ActionListFragment();
    private MineFragment mFragmentMine = new MineFragment();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CrashReport.initCrashReport(this, "900018308", false);
        setContentView(R.layout.activity_main);

        mFragment = mFragmentAction;
        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mFragment).commit();

        new LoginBGTask().execute();

        /*底部菜单事件*/
        final RadioGroup rg = (RadioGroup) findViewById(R.id.tab_menu);
        rg.check(R.id.menu_action);


        ActionInfo mActionInfo;
        rg.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                ActionInfo mActionInfo;
                switch (checkedId) {
                    case R.id.menu_task:
                        mFragment = mFragmentTask;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mFragment).commit();
                        changeMenuIcon(true, false, false);

                        //添加打点上报代码
                        mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_TASK);
                        mReport.saveOnClick(getBaseContext(), mActionInfo);
                        break;
                    case R.id.menu_action:
                        mFragment = mFragmentAction;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mFragment).commit();
                        changeMenuIcon(false, true, false);

                        //添加打点上报代码
                        mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_ACTION);
                        mReport.saveOnClick(getBaseContext(), mActionInfo);
                        break;
                    case R.id.menu_mine:
                        mFragment = mFragmentMine;
                        getSupportFragmentManager().beginTransaction().replace(R.id.main_content, mFragment).commit();
                        changeMenuIcon(false, false, true);

                        //添加打点上报代码
                        mActionInfo = new ActionInfo(ReportConstant.REPORT_MENU_MY);
                        mReport.saveOnClick(getBaseContext(), mActionInfo);
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
    public void onResume() {
        /**
         * 设置为竖屏
         */
        if(getRequestedOrientation()!= ActivityInfo.SCREEN_ORIENTATION_PORTRAIT){
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        }

        super.onResume();
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


    class LoginBGTask extends AsyncTask<String, Integer, Boolean> {
        @Override
        protected void onPreExecute() {
        }

        @Override
        protected Boolean doInBackground(String... params) {
            List<String> userInfo = FileUtil.read(MainActivity.this, GeneralConstant.FILE_NAME_ACCOUNT);
            boolean result = false;
            try {
                if (userInfo != null & !userInfo.isEmpty()) {
                    UserService userService = new UserService();
                    if (userInfo.get(0).equals("0")) {
                        result = userService.login(userInfo.get(1), userInfo.get(2), MainActivity.this);
                    } else if (userInfo.get(0).equals("1")) {
                        result = userService.login3rdAcountExist(userInfo.get(1), userInfo.get(2), userInfo.get(3), MainActivity.this);
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

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (mFragment instanceof MineFragment) {
            ((MineFragment) mFragment).getmShareAPI().onActivityResult(requestCode, resultCode, data);
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
