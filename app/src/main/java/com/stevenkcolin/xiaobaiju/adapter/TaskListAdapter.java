package com.stevenkcolin.xiaobaiju.adapter;

import android.content.Context;
import android.support.v7.widget.AppCompatCheckBox;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.stevenkcolin.xiaobaiju.R;
import com.stevenkcolin.xiaobaiju.util.DateUtil;
import com.stevenkcolin.xiaobaiju.vo.Task;

import java.util.List;

/**
 * Created by Pengfei on 2015/12/11.
 */
public class TaskListAdapter extends ArrayAdapter<Task> {
    private int resourceId;

    public TaskListAdapter(Context context, int textViewResourceId, List<Task> objects) {
        super(context, textViewResourceId, objects);
        resourceId = textViewResourceId;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        View view;
        ViewHolder  viewHolder;
        if (convertView == null) {
            view = LayoutInflater.from(getContext()).inflate(resourceId, null);
            viewHolder = new ViewHolder();
            viewHolder.checkCompleted = (AppCompatCheckBox)view.findViewById(R.id.task_completed);
            viewHolder.textDueDate = (TextView)view.findViewById(R.id.task_due_date);
            view.setTag(viewHolder);
        } else {
            view = convertView;
            viewHolder = (ViewHolder)view.getTag();
        }
        viewHolder.checkCompleted.setText(task.getTitle());
        viewHolder.checkCompleted.setChecked(task.isCompleted());
        viewHolder.textDueDate.setText(DateUtil.toLocalString(task.getDueDate()));
        return view;
    }

    class ViewHolder {
        AppCompatCheckBox checkCompleted;
        TextView textDueDate;
    }
}
