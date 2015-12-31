package com.stevenkcolin.xiaobaiju.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.dao.TaskDao;
import com.stevenkcolin.xiaobaiju.vo.Task;

/**
 * Created by Pengfei on 2015/12/14.
 */
public class TaskDetailActivity extends BaseActivity {
    private EditText editTitle;
    private EditText editDesc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        editTitle = (EditText)findViewById(R.id.task_title);
    }
    //点击返回的时候，会触发onPause事件，然后保存taskDetail中的数据
    @Override
    protected void onPause()
    {
        super.onPause();
        saveData();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                //点击菜单功能中的settings，
                // TODO: 12/31/15 添加settings的代码
                // TODO: 12/31/15 需要将menu item中的功能写在一起，而不是分开来再每个页面。
                return true;
            default:
                //实际上点击返回按钮，关掉当前的task detail页面。
                this.finish();
                return true;
        }

    }
    //保存taskdetail中的数据
    private void saveData()
    {
        //取得editTitle的值
        String title = editTitle.getText().toString();
        String desc = "";
        if (title.trim().length()>0) {
            //创建task对象, 并保存task
            Task task = new Task(title, desc);
            try {
                TaskDao.save(task);
            } catch (Exception e) {
                Log.e("Error", "onOptionsItemSelected " + e.getMessage());
            }
        }
    }
}
