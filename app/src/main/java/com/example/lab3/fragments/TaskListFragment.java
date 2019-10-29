package com.example.lab3.fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import androidx.fragment.app.ListFragment;

import com.example.lab3.R;
import com.example.lab3.task.Task;
import com.example.lab3.task.TaskAdapter;
import com.example.lab3.activities.DetailsActivity;
import com.example.lab3.task.TaskList;

public class TaskListFragment extends ListFragment {

    private Task[] tasks;
    private TaskAdapter adapter;

    public interface OnTaskListPassListener {
        void passTaskList(TaskList taskList);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        Bundle bundle = getArguments();
        this.tasks = (Task[])bundle.getParcelableArray("taskList");
        this.adapter = new TaskAdapter(tasks, getView().getContext());

        setListAdapter(adapter);
        getListView().setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), DetailsActivity.class);
                intent.putExtra("creating", false);
                intent.putExtra("task", (Task)parent.getItemAtPosition(position));
                startActivity(intent);
            }
        });

        getListView().setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                new AlertDialog.Builder(getContext())
                        .setTitle("Not Implemeted")
                        .setMessage("DELETE: no implemetation")
                        .show();
                return true;
            }
        });
    }
}
