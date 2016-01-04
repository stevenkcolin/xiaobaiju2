package com.stevenkcolin.xiaobaiju.adapter;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.support.v7.widget.AppCompatCheckBox;
import android.text.Editable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.activity.BaseActivity;
import com.stevenkcolin.xiaobaiju.activity.TaskDetailActivity;
import com.stevenkcolin.xiaobaiju.activity.TaskListActivity;
import com.stevenkcolin.xiaobaiju.util.DateUtil;
import com.stevenkcolin.xiaobaiju.vo.Task;

import java.util.List;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {
    private int resourceId;
    public String taskDesc;

    public TaskListAdapter(Context context, int textViewResourceId, List<Task> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final Task task = getItem(position);
        final View view;
        final ViewHolder  viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.checkCompleted = (AppCompatCheckBox)view.findViewById(R.id.task_completed);

            viewHolder.checkCompleted.setOnClickListener(new View.OnClickListener(){
                @Override
                public void onClick(View v) {
                    boolean status = viewHolder.checkCompleted.isChecked();
                    if (status==true) {
                        viewHolder.checkCompleted.setPaintFlags(Paint.STRIKE_THRU_TEXT_FLAG);
                    }else
                    {
                        viewHolder.checkCompleted.setPaintFlags(0);
                    }
                    task.setCompleted(status);
                    task.save();

                    // TODO: 1/4/16 添加点击后刷新ListView的代码
                }
            });

            viewHolder.checkCompleted.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View v) {
                    // TODO: 1/4/16 添加长按编辑功能
                    return true;
                }
            });
            viewHolder.textDueDate = (TextView) view.findViewById(R.id.task_due_date);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.checkCompleted.setText(task.getTitle());
        viewHolder.checkCompleted.setChecked(task.isCompleted());
        if (task.getDueDate() != null) {
            viewHolder.textDueDate.setText(DateUtil.toLocalStringDateMonth(task.getDueDate()));
        }
        return view;
    }

    class ViewHolder {
        AppCompatCheckBox checkCompleted;
        TextView textDueDate;
    }
}
