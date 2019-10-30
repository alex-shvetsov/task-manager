package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.lab3.R;
import com.example.lab3.json.Json;
import com.example.lab3.task.Task;

import java.util.Date;

public class TaskListActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);

        Json.init(this);
        //Json.put(new Task("A", new Date(), null, false));
        //Json.put(new Task("A", new Date(11111111L), null, true));
        Json.update();
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
                Intent intent = new Intent(TaskListActivity.this, DetailsActivity.class);
                intent.putExtra("creating", true);
                startActivity(intent);
                return true;
            case R.id.sortByDateMI:
                Json.sortByDate();
                Json.update();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                return true;
            case R.id.deleteDone:
                //remove all done
                Json.removeIfDone();
                Json.update();
                finish();
                overridePendingTransition(0, 0);
                startActivity(getIntent());
                return true;
            case R.id.exitMI:
                finishAffinity();
                return true;
            default:
                return super.onOptionsItemSelected(item);

        }
    }

    private void sort(boolean sorted) {

    }
}