package com.mattm2812gmail.fyp;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

public class RecyclerTypeAdapter extends RecyclerView.Adapter<RecyclerTypeAdapter.TaskListHolder> {

    private String[] mData;
    private ArrayList<TaskList> taskList;
    private Context mContext;
    private LayoutInflater mInflater;
    private ItemClickListener mClickListener;

    // data is passed into the constructor
    public RecyclerTypeAdapter(Context context, ArrayList<TaskList> taskList) {
        this.taskList = taskList;
        this.mContext = context;
    }

    // inflates the cell layout from xml when needed
    @Override
    public TaskListHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View view = layoutInflater.inflate(R.layout.recycler_tasks, parent, false);
        return new TaskListHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TaskListHolder taskListHolder, final int position) {
        final TaskList task_list = taskList.get(position);

        taskListHolder.setTaskListName(task_list.getListName());
    }

    public class TaskListHolder extends RecyclerView.ViewHolder {
        private TextView taskName;

        public TaskListHolder(View itemView) {
            super(itemView);
            taskName = itemView.findViewById(R.id.task_name);
        }

        public void setTaskListName(String name) {
            taskName.setText(name);
        }
    }

    // total number of cells
    @Override
    public int getItemCount() {
        return taskList.size();
    }


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
    String getItem(int id) {
        return mData[id];
    }

    // allows clicks events to be caught
    void setClickListener(ItemClickListener itemClickListener) {
        this.mClickListener = itemClickListener;
    }

    // parent activity will implement this method to respond to click events
    public interface ItemClickListener {
        void onItemClick(View view, int position);
    }
}
