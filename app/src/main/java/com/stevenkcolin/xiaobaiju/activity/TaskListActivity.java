package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.jeremyfeinstein.slidingmenu.lib.SlidingMenu;
import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.adapter.TaskListAdapter;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.dao.TaskDao;
import com.stevenkcolin.xiaobaiju.exception.ServerException;
import com.stevenkcolin.xiaobaiju.util.DateUtil;
import com.stevenkcolin.xiaobaiju.util.HttpUtil;
import com.stevenkcolin.xiaobaiju.vo.Task;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class TaskListActivity extends BaseActivity {

    private List<Task> taskList = new ArrayList<Task>();

    private ProgressDialog progressDialog;

    private int TASK_ADD = 1;
    private SlidingMenu slidingMenu;
    private UMShareAPI mShareAPI = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_list);
        //读取task列表，并显示在task list中
        getTaskListFromDB();
        //给圆形buttonadd task, 添加私有方法addTask(),实现添加task
        final Button mButton = (Button) findViewById(R.id.add_task);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
        //添加左滑菜单
        addSlidingMenu();
    }

    //实现从taskDetail返回父窗口时候，刷新task list页面
    @Override
    protected void onResume(){
        super.onResume();
        getTaskListFromDB();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                //add settings code
                return true;
            case R.id.refresh:
                getTaskListFromDB();
                return true;
            default:
        }
        return true;
    }
    //添加task，并launch TaskDetailActivity
    public void addTask(){
        Intent intent = new Intent(TaskListActivity.this, TaskDetailActivity.class);
        intent.setAction("add");
        startActivityForResult(intent, TASK_ADD);
    }
    //获取Task list, 并且展现在task list中
    public void getTaskListFromDB() {
        //加载所有的task
        List<Task> taskList = TaskDao.getTaskList();
        TaskListAdapter adapter = new TaskListAdapter(TaskListActivity.this, R.layout.activity_task_list_item, taskList);
        ListView listView = (ListView)findViewById(R.id.task_list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }
    //添加slidingMenu
    public void addSlidingMenu(){
        slidingMenu = new SlidingMenu(this);//创建对象
        slidingMenu.setMode(SlidingMenu.LEFT);//设定模式，SlidingMenu在左边
        slidingMenu.setBehindOffsetRes(R.dimen.slidingmenu_offset);//配置slidingmenu偏移出来的尺寸
        slidingMenu.setTouchModeAbove(SlidingMenu.TOUCHMODE_FULLSCREEN);//全屏都可以拖拽触摸，打开slidingmenu
        slidingMenu.attachToActivity(this, SlidingMenu.SLIDING_CONTENT);//附加到当前的Aty上去
        slidingMenu.setMenu(R.layout.activity_xiaobaiju_sliding_menu);


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
        openLoginPage2();
        
        //set about_us button to have a toast
        openAboutUs();
    }

    //给左侧餐单按钮about_us添加事件，欢迎打开官方网站
    private void openAboutUs(){
        findViewById(R.id.about_us).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(),R.string.txt_about_us,Toast.LENGTH_SHORT).show();
                // TODO: 1/8/16 完成官方网站的web端，并打开官方网站。 
            }
        });
    }

    //给左侧菜单按钮login_account添加事件，打开login Page
    private void openLoginPage(){
        findViewById(R.id.login_account).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(TaskListActivity.this, LoginActivity.class);
                intent.setAction("add");
                startActivityForResult(intent, TASK_ADD);

            }
        });
    }

    //给左侧菜单按钮login_account添加事件，打开login Page
    private void openLoginPage2(){
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
                mShareAPI.getPlatformInfo(TaskListActivity.this,platform,umAuthListener);
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
            if (data!=null){
                Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
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

    // TODO: 12/31/15 添加代码，实现当有网络情况下的调用后台接口功能 
    class loadTasks extends AsyncTask<Void, Void, Boolean> {
        String message;
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                JSONArray jsonArray = (JSONArray) HttpUtil.sendHttpRequest(GeneralConstant.SERVER_URL + GeneralConstant.TASK_URI, "GET", null);
                for (int i = 0; i < jsonArray.length(); i++) {
                    JSONObject jsonObject = jsonArray.getJSONObject(i);
                    Task task = new Task();
                    task.set_id(jsonObject.getString("_id"));
                    task.setTitle(jsonObject.getString("title"));
                    task.setDescrption(jsonObject.getString("description"));
                    task.setCompleted(jsonObject.getBoolean("completed"));
                    if (!jsonObject.isNull("dueDate")) {
                        task.setDueDate(DateUtil.toDate(jsonObject.getString("dueDate")));
                    }
                    taskList.add(task);
                }
                return true;
            } catch (ServerException se) {
                message = getString(R.string.error_server);
                return false;
            } catch (Exception e) {
                message = getString(R.string.error_network);
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
            if (!result) {
                Toast.makeText(getBaseContext(), message, Toast.LENGTH_SHORT).show();
            } else {
                TaskListAdapter adapter = new TaskListAdapter(TaskListActivity.this, R.layout.activity_task_list_item, taskList);
                ListView listView = (ListView)findViewById(R.id.task_list);
                listView.setAdapter(adapter);
            }
            progressDialog.dismiss();
        }

    }
}
