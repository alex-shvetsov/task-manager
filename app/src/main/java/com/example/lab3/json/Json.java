package com.example.lab3.json;

import android.content.Context;

import com.example.lab3.task.Task;
import com.example.lab3.task.TaskList;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;

public class Json {
    private static final String FILE_NAME = "data.json";
    private static TaskList taskList = new TaskList();

    public static TaskList getTaskList() {
        return taskList;
    }

    public static boolean save(Context context, TaskList taskList) {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            return true;
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return false;
    }

    public static TaskList load(Context context) {
        InputStreamReader isr = null;
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(FILE_NAME);
            isr = new InputStreamReader(fis);
            return new Gson().fromJson(isr, TaskList.class);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (isr != null) {
                try {
                    isr.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (fis != null) {
                try {
                    fis.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return null;
    }
}
