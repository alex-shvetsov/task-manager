package com.example.lab3.fragments;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.ListFragment;

import com.example.lab3.R;
import com.example.lab3.data.json.Json;
import com.example.lab3.data.TaskAdapter;

public class TaskListFragment extends ListFragment {

    private TaskAdapter adapter;
    private OnListViewItemSelected callback;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context != null) {
            this.callback = (OnListViewItemSelected)context;
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, final Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_task_list, container, false);
    }

    @Override
    public void onActivityCreated(Bundle state) {
        super.onActivityCreated(state);

        if (getView() != null) {
            // передаем позицию задачи при выборе элемента
            getListView().setOnItemClickListener((parent, view, position, id) -> {
                if (callback != null) {
                    callback.passItem(position);
                }
            });

            // удаляем задачу при длительном нажатии
            getListView().setOnItemLongClickListener((parent, view, position, id) -> {
                AlertDialog.Builder adb = new AlertDialog.Builder(getContext());
                adb.setMessage("Вы действительно хотите удалить задачу?");
                adb.setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    Json.remove(Json.get(position));
                    Json.update();
                });
                adb.setNegativeButton(android.R.string.no, (dialog, which) -> dialog.dismiss());
                adb.show();
                return true;
            });
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        // обновляем список
        adapter = new TaskAdapter(Json.get(), getView().getContext());
        setListAdapter(adapter);
    }
}
