package com.example.lab3.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lab3.R;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailsFragment extends Fragment {

    public interface OnDetailsPassedListener {
        void passDetails();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        final EditText nameET = (EditText)view.findViewById(R.id.nameText);
        EditText commentET = (EditText)view.findViewById(R.id.commentText);
        final TextView dateTV = (TextView)view.findViewById(R.id.dateText);
        final CheckBox doneCB = (CheckBox)view.findViewById(R.id.doneCheckBox);
        Button saveButton = (Button)view.findViewById(R.id.saveButton);
        Button pickButton = (Button)view.findViewById(R.id.pickButton);

        nameET.setText(getActivity().getIntent().getStringExtra("name"));
        commentET.setText(getActivity().getIntent().getStringExtra("comment"));
        dateTV.setText(getActivity().getIntent().getStringExtra("date"));
        doneCB.setChecked(getActivity().getIntent().getBooleanExtra("done", false));

        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());

                if (nameET.getText().toString().matches("") ||
                    dateTV.getText().toString().matches("")) {
                    adb.setTitle("Empty fields");
                    adb.setMessage("Fill 'name' and 'date' fields.");
                    adb.setNeutralButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
                    adb.show();
                    return;
                } else

                if (doneCB.isChecked() &&
                    !getActivity().getIntent().getBooleanExtra("done", false)) {
                    adb.setMessage("Are you sure?");

                    adb.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            // save to json
                        }
                    });

                    adb.setNegativeButton(android.R.string.cancel, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.cancel();
                        }
                    });

                    adb.show();
                    return;
                } else {
                    // save to json
                }
            }
        });

        pickButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Calendar calendar = Calendar.getInstance();
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                int d = calendar.get(Calendar.DAY_OF_MONTH);
                int m = calendar.get(Calendar.MONTH);
                int y = calendar.get(Calendar.YEAR);
                new DatePickerDialog(getActivity(), new DatePickerDialog.OnDateSetListener() {
                    @Override
                    public void onDateSet(DatePicker view, int year, int month, int dayOfMonth) {
                        dateTV.setText(sdf.format(calendar.getTime()));
                    }
                }, d, m, y).show();
            }
        });

        return view;
    }
}
