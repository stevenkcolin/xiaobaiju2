package com.stevenkcolin.xiaobaiju.activity;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;

import com.stevenkcolin.xiaobaiju.R;
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
                Task task = new Task();
                task.setTitle(editTitle.getText().toString());
                task.setDescrption(editDesc.getText().toString());
                task.save();
            default:
        }
        return true;
    }
}
