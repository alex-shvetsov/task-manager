package com.example.lab3.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.ListFragment;

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
        tasks.add(new Task("A", new Date(2000, 12, 12)));
        tasks.add(new Task("B", new Date(2005, 11, 11)));

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
