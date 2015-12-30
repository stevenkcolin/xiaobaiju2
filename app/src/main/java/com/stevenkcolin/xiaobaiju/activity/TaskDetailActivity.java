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
        editDesc = (EditText)findViewById(R.id.task_description);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.save_task:

                String title = editTitle.getText().toString();
                String desc = editDesc.getText().toString();
                Task task = new Task(title,desc);
                try
                {
                    //TaskDao.save(task);
                    task.save();
                }
                catch (Exception e)
                {
                    Log.e("Error", "onOptionsItemSelected "+e.getMessage());
                }
            default:
        }
        this.finish();

        return true;
    }
}
