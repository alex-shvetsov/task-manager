package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.lab3.R;
import com.example.lab3.data.json.Json;
import com.example.lab3.fragments.DetailsFragment;
import com.example.lab3.fragments.TaskListFragment;
import com.example.lab3.fragments.ViewDetailsFragment;
import com.example.lab3.interfaces.OnListViewItemSelected;


public class TaskListActivity extends AppCompatActivity implements OnListViewItemSelected {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);

        Json.init(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.task_list_menu, menu);
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
                Json.sortByDate();
                Json.update();
                return true;

            case R.id.removeDoneMI:
                // удалить все выполненные
                item.setChecked(Json.removeIfDone());
                Json.update();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void passItem(int position) {
        Intent intent = new Intent(this, ViewDetailsActivity.class);
        intent.putExtra("position", position);
        startActivity(intent);
    }

}