package com.example.lab3.fragments;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lab3.OnTaskDone;
import com.example.lab3.R;
import com.example.lab3.data.Task;
import com.example.lab3.data.Data;

import java.text.SimpleDateFormat;

public class ViewDetailsFragment extends Fragment {

    private TextView nameField;
    private TextView descField;
    private TextView dateField;
    private CheckBox doneChBox;

    private int position;
    private OnTaskDone onTaskDone;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            onTaskDone = (OnTaskDone)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_view_details, container, false);

        nameField = (TextView)view.findViewById(R.id.taskNameText_viewDetailsFragment);
        descField = (TextView)view.findViewById(R.id.descriptionText_viewDetailsFragment);
        dateField = (TextView)view.findViewById(R.id.taskDate_viewDetailsFragment);
        doneChBox = (CheckBox)view.findViewById(R.id.done_viewDetailsFragment);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(null);

        if (state != null) {
            position = state.getInt("position");
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }
    }

    @Override
    public void onResume() {
        super.onResume();

        Task task = Data.getAdapter().get(position);
        nameField.setText(task.getName());
        descField.setText(task.getDescription());
        dateField.setText(new SimpleDateFormat("dd/MM/yyyy").format(task.getDate()));
        doneChBox.setChecked(task.isDone());
        if (onTaskDone != null) {
            onTaskDone.blockEdit(task.isDone());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        state.putInt("position", position);
    }
}
