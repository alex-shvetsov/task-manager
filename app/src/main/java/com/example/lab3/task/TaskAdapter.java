package com.example.lab3.task;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.example.lab3.R;

public class TaskAdapter extends ArrayAdapter<Task> {

    private Context context;
    private int lastPosition = -1;

    private static class ViewHolder {
        TextView txtName;
        TextView txtDate;
        CheckBox chbDone;
    }

    public TaskAdapter(Task[] data, Context context) {
        super(context, R.layout.task_list_item, data);
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
            convertView = inflater.inflate(R.layout.task_list_item, parent, false);
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
        vHolder.txtDate.setText(task.getDate());
        vHolder.chbDone.setChecked(task.isDone());

        return convertView;
    }
}