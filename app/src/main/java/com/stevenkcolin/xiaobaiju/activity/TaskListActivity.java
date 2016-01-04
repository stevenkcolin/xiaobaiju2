package com.stevenkcolin.xiaobaiju.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.adapter.TaskListAdapter;
import com.stevenkcolin.xiaobaiju.constant.GeneralConstant;
import com.stevenkcolin.xiaobaiju.dao.TaskDao;
import com.stevenkcolin.xiaobaiju.exception.ServerException;
import com.stevenkcolin.xiaobaiju.util.DateUtil;
import com.stevenkcolin.xiaobaiju.util.HttpUtil;
import com.stevenkcolin.xiaobaiju.vo.Task;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class TaskListActivity extends BaseActivity {

    private List<Task> taskList = new ArrayList<Task>();

    private ProgressDialog progressDialog;

    private int TASK_ADD = 1;

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
