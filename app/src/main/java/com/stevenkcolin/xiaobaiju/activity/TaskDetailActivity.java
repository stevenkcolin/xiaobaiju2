package com.stevenkcolin.xiaobaiju.activity;

import android.os.Bundle;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.DatePicker;
import android.widget.EditText;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.constant.ReportConstant;
import com.stevenkcolin.xiaobaiju.dao.TaskDao;
import com.stevenkcolin.xiaobaiju.model.Task;
import com.stevenkcolin.xiaobaiju.report.ActionInfo;
import com.stevenkcolin.xiaobaiju.report.Report;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by Pengfei on 2015/12/14.
 */
public class TaskDetailActivity extends BaseActivity {
    private EditText editTitle;
    private AppCompatCheckBox checkCompleted;
    private DatePicker pickerDueDate;

    private Task task;
    private boolean isSave = true;

    public static Report mReport = Report.getInstance();
    ActionInfo mActionInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_task_detail);

        task = getIntent().getSerializableExtra("task") == null ? null : (Task)getIntent().getSerializableExtra("task");

        editTitle = (EditText)findViewById(R.id.task_title);
        checkCompleted = (AppCompatCheckBox)findViewById(R.id.task_completed);
        pickerDueDate = (DatePicker)findViewById(R.id.task_due_date);

        //添加打点上报
        checkCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkCompleted.isChecked()) {
                    mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKDETAIL_CHECKTASK);

                    boolean status = checkCompleted.isChecked();
                    String tmpStr = editTitle.getText().toString();
                    mActionInfo.param1 = String.valueOf(status);
                    mActionInfo.param2 = tmpStr;

                    mReport.saveOnClick(getApplicationContext(),mActionInfo);
                }
                else {
                    mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKDETAIL_UNCHECKTASK);

                    boolean status = checkCompleted.isChecked();
                    String tmpStr = editTitle.getText().toString();
                    mActionInfo.param1 = String.valueOf(status);
                    mActionInfo.param2 = tmpStr;

                    mReport.saveOnClick(getApplicationContext(),mActionInfo);
                }
            }
        });

        if (task != null) {
            editTitle.setText(task.getTitle());
            checkCompleted.setChecked(task.isCompleted());

            Calendar cal = Calendar.getInstance();
            cal.setTime(task.getDueDate());
            pickerDueDate.init(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH), cal.get(Calendar.DAY_OF_MONTH), new DatePicker.OnDateChangedListener() {
                @Override
                public void onDateChanged(DatePicker view, int year, int monthOfYear, int dayOfMonth) {

                }
            });
        }
    }
    //点击返回的时候，会触发onPause事件，然后保存taskDetail中的数据
    @Override
    protected void onPause()
    {
        super.onPause();
        if (validate() && isSave) {
            saveData();
        }
    }

    @Override
    protected void onStop()
    {
        super.onStop();
        //添加打点上报
        mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKDETAIL_BACK);
        mReport.saveOnClick(getApplicationContext(), mActionInfo);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_detail_menu, menu);
        if (task != null && task.getId() != null) {
            MenuItem miDelete = menu.findItem(R.id.delete_menu_item);
            miDelete.setVisible(true);
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.settings:
                //点击菜单功能中的settings，
                // TODO: 12/31/15 添加settings的代码
                // TODO: 12/31/15 需要将menu item中的功能写在一起，而不是分开来再每个页面。
                //添加打点上报
                mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKDETAIL_SETTINGS);
                mReport.saveOnClick(getApplicationContext(),mActionInfo);
                return true;
            case R.id.delete_menu_item:
                task = TaskDao.findById(task.getId());
                TaskDao.markAsDelete(task);
                isSave = false;
                finish();
                //添加打点上报
                mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKDETAIL_DELETE);
                mReport.saveOnClick(getApplicationContext(), mActionInfo);
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
        Boolean completed = checkCompleted.isChecked();
        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.YEAR, pickerDueDate.getYear());
        cal.set(Calendar.MONTH, pickerDueDate.getMonth());
        cal.set(Calendar.DAY_OF_MONTH, pickerDueDate.getDayOfMonth());
        Date dueDate = cal.getTime();
        if (title.trim().length()>0) {
            //创建task对象, 并保存task
            if (task == null) {
                task = new Task(title, "", dueDate, completed, null);
            } else {
                task = TaskDao.findById(task.getId());
                task.setTitle(title);
                task.setDueDate(dueDate);
                task.setCompleted(completed);
            }
            TaskDao.save(task);
        }
    }

    //验证表单输入
    private boolean validate() {
        boolean result = true;
        if (TextUtils.isEmpty(editTitle.getText())) {
            result = false;
        }
        return result;
    }
}
