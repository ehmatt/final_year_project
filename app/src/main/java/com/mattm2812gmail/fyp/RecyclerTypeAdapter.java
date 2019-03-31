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

        holder.taskName.setText(task_list.getListName());

        holder.parentLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Bundle data = new Bundle();
                Intent intent = new Intent(mContext, TaskListViewActivity.class);

                if (task_list.getTasks().isEmpty() != true) {
                    final ArrayList<Task> mTasks = task_list.getTasks();
                    data.putParcelableArrayList("tasks", mTasks);
                    data.putInt("position", position);
                }

                data.putString("taskList", task_list.getListName());
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

//    private static final String TAG = "BALENCIAGA";
//
//    public ItemClickListener itemClickListener;
//
//
//    private ArrayList<TaskList> taskList;
//    private Context mContext;
//    private LayoutInflater mInflater;
//    private ItemClickListener mClickListener;
//    public String taskListName;
//
//    // data is passed into the constructor
//    public RecyclerTypeAdapter(Context context, ArrayList<TaskList> taskList) {
//        this.taskList = taskList;
//        this.mContext = context;
//    }
//
//    // inflates the cell layout from xml when needed
//    @Override
//    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
//        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
//        View view = layoutInflater.inflate(R.layout.recycler_tasks, parent, false);
//        return new TaskListHolder(view);
//    }
//
//    @Override
//    public void onBindViewHolder(@NonNull TaskListHolder taskListHolder, final int position) {
//        final TaskList task_list = taskList.get(position);
//        taskListHolder.setTaskListName(task_list.getListName());
//    }
//
//    public class TaskListHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        private TextView taskName;
//        public Button btnDelete;
//        int position;
//
//        public TaskListHolder(View itemView) {
//            super(itemView);
//            taskName = itemView.findViewById(R.id.task_name);
//            taskListName = taskName.getText().toString();
//            btnDelete = itemView.findViewById(R.id.remove_task);
//            btnDelete.setOnClickListener(this);
//            itemView.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String listName = taskList.get(position).getListName();
//                    Log.v(TAG, listName);
//                    Intent intent =  new Intent(mContext, TaskListViewActivity.class);
//                    intent.putExtra("taskList", listName);
//                    mContext.startActivity(intent);
//
//                }
//            });
//        }
//
//        public void setTaskListName(String name) {
//            taskName.setText(name);
//        }
//
//        // remove item
//        @Override
//        public void onClick(View v) {
//            if (v.equals(btnDelete)){
//                taskList.remove(getAdapterPosition());
//                notifyItemRemoved(getAdapterPosition());
//                notifyItemRangeChanged(getAdapterPosition(),taskList.size());
//            }else if (v.equals(itemView)){
//                String listName = taskList.get(position).getListName();
//                Log.v(TAG, listName);
//                Intent intent =  new Intent(mContext, TaskListViewActivity.class);
//                intent.putExtra("taskList", listName);
//                mContext.startActivity(intent);
//            }
//        }
//    }

    // total number of cells
//    @Override
//    public int getItemCount() {
//        return taskList.size();
//    }


    // stores and recycles views as they are scrolled off screen
//    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
//        TextView myTextView;
//
//        ViewHolder(View itemView) {
//            super(itemView);
//            myTextView = itemView.findViewById(R.id.task_name);
//            itemView.setOnClickListener(this);
//        }
//
//        @Override
//        public void onClick(View view) {
//            if (mClickListener != null) mClickListener.onItemClick(view, getAdapterPosition());
//        }
//    }

    // convenience method for getting data at click position
//    TaskList getItem(int id) {
//        return taskList.get(id);
//    }
//
//    // allows clicks events to be caught
//    void setClickListener(ItemClickListener itemClickListener) {
//        this.mClickListener = itemClickListener;
//    }
//
//    // parent activity will implement this method to respond to click events
//    public interface ItemClickListener {
//        void onItemClick(View view, int position, String taskListName);
//    }

//    @Override
//    public void onAttach(Context context){
//        super.onAttach(context);
//
//        try{
//            itemClickListener = (ItemClickListener) getActivity();
//
//        }catch(ClassCastException e){
//
//        }
//    }

}
