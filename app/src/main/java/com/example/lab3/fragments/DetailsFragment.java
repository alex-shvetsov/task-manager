package com.example.lab3.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.lab3.R;
import com.example.lab3.data.Task;
import com.example.lab3.data.json.Json;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DetailsFragment extends Fragment {

    private EditText nameField;
    private EditText descField;
    private CheckBox doneChBox;
    private Button saveButton;
    private Button pickDateButton;

    private int position;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        nameField = (EditText)view.findViewById(R.id.taskNameText_detailsFragment);
        descField = (EditText)view.findViewById(R.id.descriptionText_detailsFragment);
        doneChBox = (CheckBox)view.findViewById(R.id.done_detailsFragment);
        saveButton = (Button)view.findViewById(R.id.saveButton_detailsFragment);
        pickDateButton = (Button)view.findViewById(R.id.pickDateButton_detailsFragment);

        return view;
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if (state != null) {
            position = state.getInt("position");
        }

        Bundle bundle = getArguments();
        if (bundle != null) {
            position = bundle.getInt("position");
        }

        // DatePicker
        pickDateButton.setOnClickListener(v -> {
            final Calendar cal = Calendar.getInstance();

            int y = cal.get(Calendar.YEAR);
            int m = cal.get(Calendar.MONTH);
            int d = cal.get(Calendar.DAY_OF_MONTH);

            DatePickerDialog dpd = new DatePickerDialog(getContext(),
                    (view, year, month, dayOfMonth) -> {
                        Calendar res = Calendar.getInstance();
                        res.set(year, month, dayOfMonth);
                        pickDateButton.setText(new SimpleDateFormat("dd/MM/yyyy")
                                      .format(res.getTime()));
                    }, y, m, d);
            dpd.getDatePicker().setMinDate(System.currentTimeMillis());
            dpd.show();
        });

        // Кнопка сохранения
        saveButton.setOnClickListener(v -> {
            if (nameField.getText().toString().matches("")) {
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setMessage("Введите данные в поле названия задачи.");
                adb.setNegativeButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
                adb.show();
                nameField.requestFocus();
                return;
            } else {
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setMessage("Подтвердите действие.");
                adb.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss());
                adb.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    // position < 0 ? создание : редактирование
                    Date date;
                    try {
                        date = new SimpleDateFormat().parse(pickDateButton.getText().toString());
                    } catch (ParseException e) {
                        date = new Date();
                    }

                    Task task = position < 0 ? new Task() : Json.get(position);
                    task.setName(nameField.getText().toString());
                    task.setDescription(descField.getText().toString());
                    task.setDate(date);
                    task.setDone(doneChBox.isChecked());

                    if (position < 0) {
                        Json.add(task);
                    }
                    Json.update();

                    getActivity().onBackPressed();
                });
                adb.show();
            }
        });

        if (position > -1) {
            Task task = Json.get(position);
            nameField.setText(task.getName());
            descField.setText(task.getDescription());
            pickDateButton.setText(new SimpleDateFormat("dd/MM/yyyy").format(task.getDate()));
            doneChBox.setChecked(task.isDone());
        }
    }

    @Override
    public void onSaveInstanceState(Bundle bundle) {
        bundle.putInt("position", position);
        super.onSaveInstanceState(bundle);
    }
}
