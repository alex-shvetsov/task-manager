package com.example.lab3.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.lab3.R;
import com.example.lab3.Task;
import com.example.lab3.TaskList;
import com.example.lab3.fragments.TaskListFragment;

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
        setContentView(R.layout.activity_task_list);

        this.taskList = new TaskList();
        taskList.addTask(new Task("A", "A", null, false));
    }

    @Override
    public void onResume() {
        super.onResume();
        // load from json into taskList
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
}