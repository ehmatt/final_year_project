package com.mattm2812gmail.fyp;

import android.R.layout;
import android.app.Activity;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;

import static android.R.layout.simple_list_item_1;

public class AddTask extends android.support.design.widget.BottomSheetDialogFragment {

    public static AddTask newInstance() {
        return new AddTask();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View v = inflater.inflate(R.layout.add_task_dialog, container,
                false);

        final EditText etNewItem = (EditText) v.findViewById(R.id.etNewItem);

        Button btn1 = (Button)v.findViewById(R.id.btn1);
        btn1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String input = etNewItem.getText().toString();

                if(!input.equals("")){
                    ((HomeActivity)getActivity()).itemsAdapter.add(input);
                    etNewItem.setText("");
                    ((HomeActivity)getActivity()).writeItems();
                }
            }
        });

        return v;

    }

}
