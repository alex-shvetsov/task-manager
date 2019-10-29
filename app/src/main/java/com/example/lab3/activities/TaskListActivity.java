package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.lab3.R;
import com.example.lab3.fragments.TaskListFragment;
import com.example.lab3.json.Json;
import com.example.lab3.task.Task;
import com.example.lab3.task.TaskList;

public class TaskListActivity extends AppCompatActivity
    implements TaskListFragment.OnTaskListPassListener {

    private TaskList taskList;

    @Override
    public void passTaskList(TaskList taskList) {
        TaskListFragment fragment = (TaskListFragment)
                getSupportFragmentManager().findFragmentById(R.id.listFragment);

        if (fragment != null) {
            Bundle bundle = new Bundle();
            bundle.putParcelableArray("taskList", taskList.get());
            fragment.setArguments(bundle);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_task_list);

        this.taskList = new TaskList();
        this.taskList.add(new Task("A", "A", null, false));
        Json.save(getBaseContext(), taskList);
        passTaskList(taskList);
    }

    @Override
    public void onResume() {
        super.onResume();
        taskList = Json.load(getBaseContext());
        passTaskList(taskList);
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
                intent.putExtra("taskList", taskList.get());
                startActivity(intent);
                return true;
            case R.id.sortByDateMI:
                //sort
                return true;
            case R.id.deleteDone:
                //remove all done
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