package com.stevenkcolin.xiaobaiju.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.activity.TaskDetailActivity;
import com.stevenkcolin.xiaobaiju.adapter.TaskListAdapter;
import com.stevenkcolin.xiaobaiju.constant.ReportConstant;
import com.stevenkcolin.xiaobaiju.dao.TaskDao;
import com.stevenkcolin.xiaobaiju.model.Task;
import com.stevenkcolin.xiaobaiju.model.User;
import com.stevenkcolin.xiaobaiju.report.ActionInfo;
import com.stevenkcolin.xiaobaiju.service.TaskService;

import java.util.List;

/**
 * Created by Pengfei on 2016/2/2.
 */
public class TaskListFragment extends BaseFragment {

    private int TASK_ADD = 1;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, null);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        //读取task列表，并显示在task list中
        fresh();
        //给圆形buttonadd task, 添加私有方法addTask(),实现添加task
        final Button mButton = (Button)getView().findViewById(R.id.add_task);
        mButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addTask();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();

        fresh();
        new UploadTasks().execute();
    }

    //添加task，并launch TaskDetailActivity
    public void addTask(){
        //打开TaskDetailActivity 来添加task详情
        Intent intent = new Intent(getActivity(), TaskDetailActivity.class);
        intent.setAction("add");
        startActivityForResult(intent, TASK_ADD);
        //添加打点上报代码
        ActionInfo mActionInfo = new ActionInfo(ReportConstant.REPORT_TASKLIST_ADDTASK);
        mReport.saveOnClick(getActivity(), mActionInfo);

    }

    //获取Task list, 并且展现在task list中
    @Override
    public void fresh() {
        //加载所有的task
        List<Task> taskList = TaskDao.getTaskList();
        TaskListAdapter adapter = new TaskListAdapter(getActivity(), R.layout.sub_task_list_item, taskList);
        ListView listView = (ListView)getView().findViewById(R.id.task_list);
        listView.setAdapter(adapter);
        adapter.notifyDataSetChanged();

    }

    class UploadTasks extends AsyncTask<Void, Void, Boolean> {
        @Override
        protected Boolean doInBackground(Void... params) {
            try {
                if (User.getUser().getId() != null) {
                    TaskService taskService = new TaskService();
                    return taskService.uploadTask(getContext());
                }
                return false;
            } catch (Exception e) {
                return false;
            }
        }

        @Override
        protected void onPostExecute(Boolean result) {
        }

    }

}
