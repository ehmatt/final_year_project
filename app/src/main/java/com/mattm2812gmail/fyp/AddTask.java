package com.mattm2812gmail.fyp;

import android.R.layout;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomSheetDialogFragment;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import org.apache.commons.io.FileUtils;
import org.w3c.dom.Text;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

import static android.R.layout.simple_list_item_1;

public class AddTask extends android.support.design.widget.BottomSheetDialogFragment {

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
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_task_dialog, container,
                false);

        addMainTask = v.findViewById(R.id.addTask);
        addSubtask = v. findViewById(R.id.addSubtask);
        tvDisplayDate = v.findViewById(R.id.tvDisplayDate);

        Button btn_confirm = v.findViewById(R.id.btn_confirm);
        btn_confirm.setTextColor(Color.WHITE);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mainTask = addMainTask.getText().toString();
                String subtask = addSubtask.getText().toString();
                String date = tvDisplayDate.getText().toString();

                Task newTask = new Task(mainTask, subtask, date);

                mInputListener.sendInput(newTask);
                dismiss();
            }
        });

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
