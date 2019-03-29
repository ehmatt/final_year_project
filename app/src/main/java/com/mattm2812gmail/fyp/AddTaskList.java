package com.mattm2812gmail.fyp;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

public class AddTaskList extends DialogFragment {

    public interface OnInputListener{
        void sendInput(String input);

    }

    public OnInputListener mInputListener;

    public Button btn_confirm;
    public EditText eListName;
    public String listName;

    public static AddTaskList newInstance() {
        return new AddTaskList();
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState){
        View v = inflater.inflate(R.layout.add_task_list, container, false);
        v.setMinimumWidth(Integer.parseInt("320"));

        eListName = v.findViewById(R.id.create_list);
        btn_confirm = v.findViewById(R.id.btn_confirm);
        btn_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                TaskList newTaskList = new TaskList(listName);
                ((HomeActivity)getActivity()).taskList.add(new TaskList(listName));
                ((HomeActivity)getActivity()).recyclerTypeAdapter.notifyDataSetChanged();

//                listName = eListName.getText().toString();
//                mInputListener.sendInput(listName);
                dismiss();
            }
        });
        return v;
    }

    @Override
    public void onAttach(Context context){
        super.onAttach(context);

        try{
            mInputListener = (OnInputListener) getActivity();

        }catch(ClassCastException e){

        }
    }

}
