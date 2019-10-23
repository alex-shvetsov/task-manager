package com.example.lab3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import androidx.fragment.app.Fragment;

import com.example.lab3.Json;
import com.example.lab3.R;
import com.example.lab3.Task;
import com.example.lab3.TaskAdapter;
import com.example.lab3.activities.TaskActivity;

import java.sql.Date;
import java.util.ArrayList;

public class TaskListFragment extends Fragment {

    private ArrayList<Task> tasks;
    private ListView taskList;
    private static TaskAdapter adapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_task, container, true);

        tasks = Json.parse(null);
        tasks.add(new Task("A", "2000/12/12", null, false));
        tasks.add(new Task("B", "2000/12/12", "comment", false));

        adapter = new TaskAdapter(tasks, view.getContext());
        taskList = (ListView)view.findViewById(R.id.taskListView);
        taskList.setAdapter(adapter);

        taskList.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
            Task task = (Task)parent.getItemAtPosition(position);

            Intent intent = new Intent(getActivity(), TaskActivity.class);
            intent.putExtra("name", task.getName());
            intent.putExtra("comment", task.getComment());
            intent.putExtra("date", task.getDate().toString());
            intent.putExtra("done", task.isDone());
            startActivity(intent);
            }
        });

        taskList.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                // remove
                return false;
            }
        });

        return view;
    }

    public static TaskAdapter getAdapter() {
        return adapter;
    }
}
