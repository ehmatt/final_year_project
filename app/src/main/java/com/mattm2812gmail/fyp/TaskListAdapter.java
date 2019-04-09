package com.mattm2812gmail.fyp;

import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class TaskListAdapter extends RecyclerView.Adapter<TaskListAdapter.ViewHolder> {

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

        // remove tasks and subtasks
        holder.subtaskRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String subRemove = (mTaskList.get(position)).getSubtask();
                holder.tvSubtask.setText("");
                holder.subtaskRemove.setVisibility(View.GONE);
            }
        });

        holder.taskRemove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                holder.removeTaskDatabase(position);
                mTaskList.remove(position);
                notifyItemRemoved(position);
                notifyItemRangeChanged(position, mTaskList.size());
            }
        });

        // set task as a priority
        holder.priority.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int color = Color.TRANSPARENT;
                Drawable background = holder.priority.getBackground();
                if (background instanceof ColorDrawable)
                    color = ((ColorDrawable) background).getColor();
                if (color == Color.RED){
                    holder.parentLayout.setBackgroundColor(Color.WHITE);
                }else {
                    holder.parentLayout.setBackgroundColor(Color.RED);
                }
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
        Button taskRemove, subtaskRemove, priority, btnDate;
        LinearLayout parentLayout;

        private FirebaseAuth mFirebaseAuth = FirebaseAuth.getInstance();
        private FirebaseUser mFirebaseUser = mFirebaseAuth.getCurrentUser();
        private String userID = mFirebaseUser.getUid();
        private DatabaseReference mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(userID);

        // remove a task from the database
        public void removeTaskDatabase(int position){
            mDatabase.child("TaskList").child(mTaskList.get(position).getParentName()).child("mHashMap").child(mTaskList.get(position).getTask()).removeValue();
        }


        public ViewHolder(View itemView) {
            super(itemView);
            tvTask = itemView.findViewById(R.id.task_name);
            tvSubtask = itemView.findViewById(R.id.subtask);
            tvDate = itemView.findViewById(R.id.btnDateView);
            taskRemove = itemView.findViewById(R.id.task_remove);
            subtaskRemove = itemView.findViewById(R.id.subtask_remove);
            parentLayout = itemView.findViewById(R.id.task_view);
            priority = itemView.findViewById(R.id.priority);
            btnDate = itemView.findViewById(R.id.btnDateView);
            String subRemove;
        }
    }
}
