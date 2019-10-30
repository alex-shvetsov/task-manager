package com.example.lab3.fragments;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.example.lab3.R;
import com.example.lab3.json.Json;
import com.example.lab3.task.Task;

import java.text.SimpleDateFormat;
import java.util.Calendar;

public class DetailsFragment extends Fragment {

    public enum Mode {
        NULL, CREATE, VIEW;

        private View[] views;
        private Button saveButton;
        private CheckBox doneCheckBox;

        void set(Mode mode) {
            switch (mode) {
                case CREATE:
                    listenToCheckBox(false);
                    hideSaveButton(false);
                    setElementsInteractable(true);
                    break;

                case VIEW:
                    listenToCheckBox(true);
                    hideSaveButton(!doneCheckBox.isChecked());
                    setElementsInteractable(false);
                    break;

                default: break;
            }
        }

        void setViewsToInteract(View[] views) {
            this.views = views;
        }

        void setSaveButton(Button saveButton) {
            this.saveButton = saveButton;
        }

        void setDoneCheckBox(CheckBox doneCheckBox) {
            this.doneCheckBox = doneCheckBox;
        }

        private void setElementsInteractable(boolean interactable) {
            for (View v : views) {
                v.setEnabled(interactable);
            }
        }

        private void hideSaveButton(boolean hide) {
            saveButton.setVisibility(hide ? View.INVISIBLE : View.VISIBLE);
        }

        private void listenToCheckBox(boolean listen) {
            doneCheckBox.setOnCheckedChangeListener(
                    listen ? (buttonView, isChecked) -> hideSaveButton(!isChecked) : null
            );
        }
    }

    private EditText nameEditText;
    private EditText commentEditText;
    private TextView dateTextView;
    private CheckBox doneCheckBox;
    private Button saveButton;
    private Button pickDateButton;

    private Mode mode = Mode.NULL;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_details, container, false);

        nameEditText = (EditText)view.findViewById(R.id.nameText);
        commentEditText = (EditText)view.findViewById(R.id.commentText);
        dateTextView = (TextView)view.findViewById(R.id.dateText);
        doneCheckBox = (CheckBox)view.findViewById(R.id.doneCheckBox);
        saveButton = (Button)view.findViewById(R.id.saveButton);
        pickDateButton = (Button)view.findViewById(R.id.pickButton);

        View[] views = {nameEditText, commentEditText, pickDateButton};

        // Режим просмотра
        mode.set(Mode.VIEW);
        mode.setDoneCheckBox(doneCheckBox);
        mode.setSaveButton(saveButton);
        mode.setViewsToInteract(views);

        // Получаем задачу по позиции в массиве
        Task task = Json.get()[getActivity().getIntent().getIntExtra("taskPosition", 0)];

        // Заполнение полей
        nameEditText.setText(task.getName());
        commentEditText.setText(task.getComment());
        dateTextView.setText(task.getDate());
        doneCheckBox.setChecked(task.isDone());

        saveButton.setOnClickListener(v -> {
            AlertDialog.Builder adb = new AlertDialog.Builder(getActivity());

            if (nameEditText.getText().toString().matches("") ||
                dateTextView.getText().toString().matches("")) {
                adb.setMessage("Введите данные в поля имени и даты выполнения.");
                adb.setNeutralButton(android.R.string.ok, (dialog, which) -> dialog.dismiss());
                adb.show();
                return;
            } else {
                adb.setMessage("Are you sure?" +
                        (doneCheckBox.isChecked() && !task.isDone() ? " Task cannot be undone." : ""));

                adb.setPositiveButton(android.R.string.ok, (dialog, which) -> {
                    Json.put(new Task(
                            nameEditText.getText().toString(),
                            dateTextView.getText().toString(),
                            commentEditText.getText().toString(),
                            doneCheckBox.isChecked()
                    ));
                    Json.update();
                });

                adb.setNegativeButton(android.R.string.cancel, (dialog, which) -> dialog.cancel());
                adb.show();
                return;
            }
        });

        pickDateButton.setOnClickListener(v -> {
            Calendar calendar = Calendar.getInstance();
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
            int d = calendar.get(Calendar.DAY_OF_MONTH);
            int m = calendar.get(Calendar.MONTH);
            int y = calendar.get(Calendar.YEAR);
            new DatePickerDialog(getActivity(), (view1, year, month, dayOfMonth) ->
                    dateTextView.setText(sdf.format(calendar.getTime())), d, m, y)
                    .show();
        });

        return view;
    }

    public void setMode(Mode mode) {
        this.mode = mode;
        this.mode.set(mode);
    }
}
