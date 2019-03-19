package com.mattm2812gmail.fyp;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class TaskListAdapter extends ArrayAdapter<Task> {

    private Context mContext;
    int mResource;

    public TaskListAdapter(Context context, int resource, ArrayList<Task> objects) {
        super(context, resource, objects);
        mContext = context;
        mResource = resource;
    }

    @Nullable
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        String task = getItem(position).getTask();
        String subtask = getItem(position).getSubtask();
        String date = getItem(position).getDate();

        Task new_task = new Task(task, subtask, date);

        LayoutInflater inflater = LayoutInflater.from(mContext);
        convertView = inflater.inflate(mResource, parent,false);

        TextView tvTask = (TextView) convertView.findViewById(R.id.textView1);
        TextView tvSubTask = (TextView) convertView.findViewById(R.id.textView2);
        TextView tvDate = (TextView) convertView.findViewById(R.id.textView3);

        tvTask.setText(task);
        tvSubTask.setText(subtask);
        tvDate.setText(date);

        return convertView;
    }
}
