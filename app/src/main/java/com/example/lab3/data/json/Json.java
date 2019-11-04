package com.example.lab3.data.json;

import android.content.Context;

import com.example.lab3.data.Task;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Json {
    private static final String FILENAME = "data.json";
    private static Context context;
    private static TaskList taskList;

    /**
     * Обновление происходит при вызове метода update, где переменной присваивается true.
     * Переменной присваивается false при обновлении списка задач.
     */
    private static boolean updated = true;

    /**
     * Установлено в true, если список был инициализирован из JSON.
     */
    private static boolean loaded = false;

    // Инициализация данных: загрузка JSON
    public static boolean init(Context context) {
        if (!loaded) {
            Json.context = context;
            loaded = load();
        }
        return false;
    }

    // Добавить задачу в список
    public static boolean add(Task task) {
        if (loaded) {
            boolean success = taskList.add(task);
            updated = !success && updated;
            return success;
        }
        return false;
    }

    // Удалить задачу из списка
    public static boolean remove(Task task) {
        if (loaded) {
            boolean success = taskList.remove(task);
            updated = !success && updated;
            return success;
        }
        return false;
    }

    // Удалить выполненные задачи
    public static boolean removeIfDone(){
        if(loaded){
            boolean success = taskList.removeIfDone();
            updated = !success && updated;
            return success;
        }
        return false;
    }

    // Отсортировать по дате
    public static boolean sortByDate(){
        if(loaded){
            boolean success = taskList.sortByDate();
            updated = !success && updated;
            return success;
        }
        return false;
    }

    // Инициировать обновление списка
    public static boolean update() {
        if (loaded) {
            save();
            return updated = true;
        }
        return false;
    }

    // Получить список задач в виде массива
    public static Task[] get() {
        if (loaded && updated && taskList != null) {
            return taskList.get();
        }
        return null;
    }

    // Получить задачу по позиции
    public static Task get(int position) {
        Task[] tasks = get();
        if (tasks != null && tasks.length > position) {
            return tasks[position];
        }
        return null;
    }

    // Сохранение JSON в файл
    private static boolean save() {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
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

    // Загрузка JSON из файла
    private static boolean load() {
        InputStreamReader isr = null;
        FileInputStream fis = null;

        try {
            fis = context.openFileInput(FILENAME);
            isr = new InputStreamReader(fis);
            taskList = new Gson().fromJson(isr, TaskList.class);
            return taskList != null;
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

        return false;
    }
}
