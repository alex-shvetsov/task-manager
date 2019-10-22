package com.example.lab3.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lab3.R;

public class DetailsFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        EditText nameET = (EditText)view.findViewById(R.id.nameText);
        EditText commentET = (EditText)view.findViewById(R.id.commentText);
        TextView dateTV = (TextView)view.findViewById(R.id.dateText);
        CheckBox doneCB = (CheckBox)view.findViewById(R.id.doneCheckBox);

        nameET.setText(getArguments().getString("name"));
        commentET.setText(getArguments().getString("comment"));
        dateTV.setText(getArguments().getString("date"));
        doneCB.setChecked(getArguments().getBoolean("done"));

        return view;
    }
}
