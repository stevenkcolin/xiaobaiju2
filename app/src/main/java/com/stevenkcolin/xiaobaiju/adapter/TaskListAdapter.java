package com.stevenkcolin.xiaobaiju.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.activity.TaskDetailActivity;
import com.stevenkcolin.xiaobaiju.dao.TaskDao;
import com.stevenkcolin.xiaobaiju.util.DateUtil;
import com.stevenkcolin.xiaobaiju.vo.Task;

import java.util.List;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {
    private int resourceId;
    private List<Task> taskList;

    public TaskListAdapter(Context context, int textViewResourceId, List<Task> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
        taskList = objects;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = taskList.get(position);
        final View view;
        final ViewHolder  viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.checkCompleted = (AppCompatCheckBox)view.findViewById(R.id.task_completed);
            viewHolder.textDueDate = (TextView) view.findViewById(R.id.task_due_date);
            viewHolder.textTitle = (TextView)view.findViewById(R.id.task_title);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.textTitle.setText(task.getTitle());
        viewHolder.checkCompleted.setChecked(task.isCompleted());
        //根据task的状态，决定checkCompleted的是否有划线，和是否为斜体。
        if (task.isCompleted()) {
            viewHolder.textTitle.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            viewHolder.textTitle.setTypeface(null, Typeface.BOLD_ITALIC);
        } else {
            viewHolder.textTitle.setPaintFlags(0);
            viewHolder.textTitle.setTypeface(null,Typeface.NORMAL);
        }

        //添加checkbox的按键事件
        viewHolder.checkCompleted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean status = viewHolder.checkCompleted.isChecked();
                task.setCompleted(status);
                task.save();

                List<Task> taskList = TaskDao.getTaskList();
                TaskListAdapter.this.clear();
                TaskListAdapter.this.addAll(taskList);
            }
        });

        //添加checkbox的长按事件
        viewHolder.textTitle.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getContext(), TaskDetailActivity.class);
                intent.putExtra("task", task);
                getContext().startActivity(intent);
            }
        });

        if (task.getDueDate() != null) {
            viewHolder.textDueDate.setText(DateUtil.toLocalStringDateMonth(task.getDueDate()));
        }
        return view;
    }

    class ViewHolder {
        AppCompatCheckBox checkCompleted;
        TextView textDueDate;
        TextView textTitle;
    }
}
