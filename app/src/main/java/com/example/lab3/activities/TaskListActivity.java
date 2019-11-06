package com.example.lab3.activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.SpinnerAdapter;

import androidx.appcompat.app.AppCompatActivity;

import com.example.lab3.OnDataSorted;
import com.example.lab3.OnListViewItemSelected;
import com.example.lab3.R;
import com.example.lab3.data.Data;
import com.example.lab3.data.TaskAdapter;

public class TaskListActivity extends AppCompatActivity implements OnListViewItemSelected, OnDataSorted {

    private boolean sorted;
    private Menu menu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);
        setTitle("Моя Прога 1.0");

        Data.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
        this.menu = menu;

        Spinner spinner = (Spinner) menu.findItem(R.id.spinner).getActionView();
        SpinnerAdapter spinnerAdapter = ArrayAdapter.createFromResource(
                getSupportActionBar().getThemedContext(),
                R.array.spinner_choices,
                android.R.layout.simple_spinner_dropdown_item
        );
        spinner.setAdapter(spinnerAdapter);
        spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                switch (position) {
                    case 0:
                        Data.getAdapter().filter(TaskAdapter.Filter.ALL);
                        break;

                    case 1:
                        Data.getAdapter().filter(TaskAdapter.Filter.FULFILLED);
                        break;

                    case 2:
                        Data.getAdapter().filter(TaskAdapter.Filter.UNFULFILLED);
                        break;
                }

                setSorted(false);
                Data.getAdapter().update();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) { }
        });

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.createMI:
                // создание новой задачи
                passItem(-1);
                return true;

            case R.id.sortByDateMI:
                // сортировка по дате
                if (!sorted) {
                    Data.getAdapter().sortByDate();
                } else {
                    Data.getAdapter().update();
                }
                return true;

            case R.id.removeDoneMI:
                // удалить все выполненные
                Data.removeAllDone(true);
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void passItem(int position) {
        Intent intent = new Intent(this,
                position < 0 ? DetailsActivity.class : ViewDetailsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

    @Override
    public void onSaveInstanceState(Bundle state) {
        super.onSaveInstanceState(state);
        state.putBoolean("sorted", sorted);
    }

    @Override
    public void onRestoreInstanceState(Bundle state) {
        this.sorted = state.getBoolean("sorted");
    }

    @Override
    public void setSorted(boolean sorted) {
        this.sorted = sorted;

        if (menu != null) {
            MenuItem sortMI = (MenuItem)menu.findItem(R.id.sortByDateMI);
            sortMI.setChecked(sorted);
        }
    }
}