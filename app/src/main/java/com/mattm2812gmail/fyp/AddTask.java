package com.mattm2812gmail.fyp;

import android.R.layout;
import android.app.Activity;
import android.app.DatePickerDialog;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
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

import static android.R.layout.simple_list_item_1;

public class AddTask extends android.support.design.widget.BottomSheetDialogFragment {

    public static AddTask newInstance() {
        return new AddTask();
    }

    public TextView tvDisplayDate;
    public String selectedDate;
    public static final int REQUEST_CODE = 11;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_task_dialog, container,
                false);

//        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.BottomSheetDialog);
//        return super.onCreateDialog(savedInstanceState);


        final EditText etNewItem = v.findViewById(R.id.etNewItem);
        tvDisplayDate = v.findViewById(R.id.tvDisplayDate);

        final String finalDate = tvDisplayDate.getText().toString();

        Button btn_confirm = (Button)v.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etNewItem.getText().toString();
                String input2 = "testing";
                String date = finalDate;
//                Date date = new SimpleDateFormat("MM-dd-yyyy").parse(finalDate);
//                Date date = tvDisplayDate.getText();


                if(!input.equals("")){
                    Task task1 = new Task(input, input2, date);
                    ((HomeActivity)getActivity()).tasks.add(task1);
//                    etNewItem.setText("");
                    ((HomeActivity)getActivity()).writeItems();
                }

            }
        });

        Button btn_date = (Button)v.findViewById(R.id.btn_date);
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
}
