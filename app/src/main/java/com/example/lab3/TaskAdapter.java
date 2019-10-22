package com.example.lab3;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import java.util.ArrayList;

public class TaskAdapter extends ArrayAdapter<Task> {

    private ArrayList<Task> data;
    private Context context;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        CheckBox chbDone;
    }

    public TaskAdapter(ArrayList<Task> data, Context context) {
        super(context, R.layout.task_item, data);
        this.data = data;
        this.context = context;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        Task task = getItem(position);
        ViewHolder vHolder;
        final View result;

        if (convertView == null) {
            vHolder = new ViewHolder();
            LayoutInflater inflater = LayoutInflater.from(getContext());
            convertView = inflater.inflate(R.layout.task_item, parent, false);
            vHolder.txtName = (TextView)convertView.findViewById(R.id.name);
            vHolder.txtDate = (TextView)convertView.findViewById(R.id.date);
            vHolder.chbDone = (CheckBox) convertView.findViewById(R.id.done);

            result = convertView;
            convertView.setTag(vHolder);
        } else {
            vHolder = (ViewHolder)convertView.getTag();
            result = convertView;
        }

        Animation animation = AnimationUtils.loadAnimation(context,
                (position > lastPosition) ? R.anim.up_from_bottom : R.anim.down_from_top);
        result.startAnimation(animation);
        lastPosition = position;

        vHolder.txtName.setText(task.getName());
        vHolder.txtDate.setText(task.getDate().toString());
        vHolder.chbDone.setChecked(task.isDone());

        return convertView;
    }

    public void putTask(Task task) {
        data.add(task);
    }

    public void removeTask(Task task) {
        data.remove(task);
    }
}
