package com.mattm2812gmail.fyp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;

public class RecyclerTypeAdapter extends RecyclerView.Adapter<RecyclerTypeAdapter.ViewHolder> {

    private static final String TAG = "RecyclerTypeAdapter";

    private ArrayList<TaskList> mTaskList;
    private Context mContext;

    public RecyclerTypeAdapter(Context context, ArrayList<TaskList> taskList) {
        mTaskList = taskList;
        mContext = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.recycler_tasks, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, final int position) {
        final TaskList task_list = mTaskList.get(position);

//        holder.taskName.setText(task_list.getListName());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                Intent intent = new Intent(mContext, TaskListViewActivity.class);

//                if (task_list.getTasks().isEmpty() != true) {
////                    final ArrayList<Task> mTasks = task_list.getTasks();
////                    data.putParcelableArrayList("tasks", mTasks);
//                    data.putInt("position", position);
//                }

//                data.putString("taskList", task_list.getListName());
                intent.putExtras(data);
                mContext.startActivity(intent);
            }
        });

        holder.delete.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
//                mTaskList.remove(getAdapterPosition());
//                notifyItemRemoved(getAdapterPosition());
//                notifyItemRangeChanged(getAdapterPosition(),mTaskList.size());
            }
        });
    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView taskName;
        TextView firstTask;
        Button delete;
        ConstraintLayout parentLayout;

        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            firstTask = itemView.findViewById(R.id.first_task);
            delete = itemView.findViewById(R.id.remove_task);
            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }

}
