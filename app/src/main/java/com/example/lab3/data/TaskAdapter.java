package com.example.lab3.data;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lab3.R;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TaskAdapter extends ArrayAdapter<Task>  {

    private Task[] data;
    private Filter lastFilter;

    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        CheckBox chbDone;
    }

    TaskAdapter(Task[] data, Context context) {
        super(context, R.layout.task_list_item, new ArrayList<>(Arrays.asList(data)));
        this.data = data;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        // организация внешнего вида строки списка
        Task task = getItem(position);
        ViewHolder vHolder;

        if (convertView == null) {
            vHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
            vHolder.txtName = (TextView)convertView.findViewById(R.id.name_listViewItemComponent);
            vHolder.txtDate = (TextView)convertView.findViewById(R.id.date_listViewItemComponent);
            vHolder.chbDone = (CheckBox) convertView.findViewById(R.id.done_listViewItemComponent);
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder)convertView.getTag();
        }

        vHolder.txtName.setText(task.getName());
        vHolder.txtDate.setText(new SimpleDateFormat("dd/MM/yyyy").format(task.getDate()));
        vHolder.chbDone.setChecked(task.isDone());

        return convertView;
    }

    public Task get(int position) {
        return position < data.length && position >= 0 ? data[position] : null;
    }

    private ArrayList<Task> getList() {
        return new ArrayList<>(Arrays.asList(data));
    }

    public void update() {
        filter();
    }

    private void update(Task[] data) {
        // обновление данных и оповещение об изменении
        this.data = data;

        clear();
        addAll(data);
        notifyDataSetChanged();
    }

    private void updateList(List<Task> data) {
        update(data.toArray(new Task[0]));
    }

    public void filter(Filter filter) {
        if (filter == null) return;

        this.lastFilter = filter;

        if (filter == Filter.ALL) {
            // если нужно отобразить все задачи, просто обновляем список
            update(Data.get());
        } else {
            // фильтруем (не)выполненные задачи
            ArrayList<Task> tasks = new ArrayList<>();
            for (Task t : Data.get()) {
                // fulfilled && done == true
                // unfulfilled && !done == true
                if (filter == Filter.FULFILLED == t.isDone()) {
                    tasks.add(t);
                }
            }
            updateList(tasks);
        }
    }

    public void filter() {
        filter(lastFilter);
    }

    public void sortByDate() {
        // сортируем по дате
        ArrayList<Task> data = getList();
        Collections.sort(data, (task, t1)
                -> Long.compare(task.getDate().getTime(), t1.getDate().getTime()));
        updateList(data);
    }

    public enum Filter {
        ALL, FULFILLED, UNFULFILLED
    }
}
