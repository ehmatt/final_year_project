package com.mattm2812gmail.fyp;

import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class AddTask extends android.support.design.widget.BottomSheetDialogFragment {
    public static AddTask newInstance() {
        return new AddTask();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.add_task_dialog, container,
                false);

        // get the views and attach the listener

        return view;

    }

//    DialogInterface.OnClickListener dialogClickListener = new DialogInterface.OnClickListener() {
//        @Override
//        public void onClick(DialogInterface dialog, int which) {
//
//
//
//        }
//    };

}
