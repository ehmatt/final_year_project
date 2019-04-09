package com.mattm2812gmail.fyp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class AddTask extends android.support.design.widget.BottomSheetDialogFragment {

    // interface for sending data
    public interface OnInputListener{
        void sendInput(Task input);
    }

    public OnInputListener mInputListener;

    public static AddTask newInstance() {
        return new AddTask();
    }

    public TextView tvDisplayDate;
    public EditText addMainTask, addSubtask;
    public String selectedDate, finalDate;
    public static final int REQUEST_CODE = 11;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_task_dialog, container,
                false);

        final TaskListViewActivity activity = (TaskListViewActivity)getActivity();

        addMainTask = v.findViewById(R.id.addTask);
        addSubtask = v. findViewById(R.id.addSubtask);
        tvDisplayDate = v.findViewById(R.id.tvDisplayDate);

        Button btn_confirm = v.findViewById(R.id.btn_confirm);
        btn_confirm.setTextColor(Color.WHITE);

        // confirm task
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainTask = addMainTask.getText().toString();
                String subtask = addSubtask.getText().toString();
                String date = tvDisplayDate.getText().toString();
                String parentName = activity.getMyData();

                Task newTask = new Task(mainTask, subtask, date, parentName);

                mInputListener.sendInput(newTask);
                dismiss();
            }
        });

        //open date pick fragment
        Button btn_date = v.findViewById(R.id.btn_date);
        btn_date.setTextColor(Color.WHITE);
        btn_date.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v){
                DialogFragment newFragment = new DatePickerFragment();
                newFragment.setTargetFragment(AddTask.this, REQUEST_CODE);
                newFragment.show(getActivity().getSupportFragmentManager(), "datePicker");

            }
        });

        return v;

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // check for the results
            // get date from string
            selectedDate = data.getStringExtra("selectedDate");
            // set the value of the editText
            tvDisplayDate.setText(selectedDate);

    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            mInputListener = (AddTask.OnInputListener) getActivity();

        }catch(ClassCastException e){

        }
    }

}
