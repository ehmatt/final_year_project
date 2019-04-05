package com.mattm2812gmail.fyp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {
    private static final String TAG = "TaskListAdapter";



    private Context mContext;
    private ArrayList<Task> mTaskList;

    public TaskListAdapter(Context context, ArrayList<Task> taskList) {
        mContext = context;
        mTaskList = taskList;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.task_view_layout, parent, false);
        ViewHolder holder = new ViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        final Task mTask = mTaskList.get(position);
        holder.tvTask.setText(mTask.getTask());
        holder.tvSubtask.setText((mTask.getSubtask()));
        holder.tvDate.setText(mTask.getDate());

        holder.priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.parentLayout.setBackgroundColor(Color.RED);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mTaskList.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView tvTask;
        TextView tvSubtask;
        TextView tvDate;
        Button taskRemove, subtaskRemove, priority;
        LinearLayout parentLayout;

        private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        private FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        private String userID = mFirebaseUser.getUid();
        private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);


        public ViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.task_name);
            tvSubtask = itemView.findViewById(R.id.subtask);
            tvDate = itemView.findViewById(R.id.btnDateView);
            taskRemove = itemView.findViewById(R.id.task_remove);
            subtaskRemove = itemView.findViewById(R.id.subtask_remove);
            parentLayout = itemView.findViewById(R.id.task_view);
            priority = itemView.findViewById(R.id.priority);

//            TextView parentListName = parentLayout.findViewById(R.id.list_name);
//            String listName = parentListName.getText().toString();
//            Log.d(TAG, "ViewHolder: " + listName);

            taskRemove.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
//                    mDatabase.child("TaskList").child()
                    mTaskList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mTaskList.size());
                }
            });

//            parentLayout = itemView.findViewById(R.id.parent_layout);
        }
    }
}
