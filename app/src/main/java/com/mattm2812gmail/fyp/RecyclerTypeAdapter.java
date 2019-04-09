package com.mattm2812gmail.fyp;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.constraint.ConstraintLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.HashMap;

public class RecyclerTypeAdapter extends RecyclerView.Adapter<RecyclerTypeAdapter.ViewHolder> {

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

        // if the list has no tasks
        // set view to 0
        holder.taskName.setText(task_list.getTaskListName());
        if (task_list.getmHashMap() == null){
            holder.firstTask.setText("0");
        }else{
            int size = task_list.getmHashMap().size();
            holder.firstTask.setText(Integer.toString(size));
        }

        // on click, bundle task data and send to activity
        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                Intent intent = new Intent(mContext, TaskListViewActivity.class);

                if (task_list.getmHashMap() != null) {
                    final HashMap<String, HashMap<String, String>> mTasks = task_list.getmHashMap();
                    data.putSerializable("tasks", mTasks);
                    data.putInt("position", position);
                }

                data.putString("taskList", task_list.getTaskListName());
                intent.putExtras(data);
                mContext.startActivity(intent);
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

        private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        private FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        private String userID = mFirebaseUser.getUid();
        private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);


        public ViewHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
            firstTask = itemView.findViewById(R.id.first_task);
            delete = itemView.findViewById(R.id.remove_task_list);
            parentLayout = itemView.findViewById(R.id.parent_layout);

            // remove task from array list and data base
            delete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    TaskList removeList = mTaskList.get(getAdapterPosition());
                    mDatabase.child("TaskList").child(removeList.getTaskListName()).removeValue();
                    mTaskList.remove(getAdapterPosition());
                    notifyItemRemoved(getAdapterPosition());
                    notifyItemRangeChanged(getAdapterPosition(), mTaskList.size());

                }
            });
        }
    }

}
