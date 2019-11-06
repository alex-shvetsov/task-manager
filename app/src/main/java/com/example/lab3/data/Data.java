package com.example.lab3.data;

import android.content.Context;

import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Data {
    private static final String FILENAME = "data2.json";
    private static Context context;
    private static TaskList taskList;       // Хранит ВСЕ данные
    private static TaskAdapter adapter;     // Адаптер + хранит данные, представленные в ListView

    /**
     * Обновление происходит при вызове метода Supdate, где переменной присваивается true.
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
            Data.context = context;

            InputStreamReader streamReader = null;
            FileInputStream inputStream = null;
            boolean again = true;

            while (!loaded) {
                try {
                    // исключение, если файл не существует
                    inputStream = context.openFileInput(FILENAME);
                    streamReader = new InputStreamReader(inputStream);
                    // получаем данные из файла
                    taskList = new Gson().fromJson(streamReader, TaskList.class);
                    // файл успешно загружен
                    loaded = true;
                } catch (IOException e) {
                    if (again) {
                        loaded = false;     // файл не загружен
                        again = false;      // в след. раз не пытаться повторно сохранять файл
                        save();             // создаем файл
                        continue;           // пытаемся заново
                    } else break;           // выходим из цикла: файл невозможно загрузить
                }
            }

            if (streamReader != null) {
                try {
                    streamReader.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }

            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        return loaded;
    }

    // Добавить задачу в список
    public static void add(Task task, boolean save) {
        if (loaded) {
            taskList.add(task);
            if (save) save();
        }
    }

    // Удалить задачу из списка
    public static void remove(Task task, boolean save) {
        if (loaded) {
            taskList.remove(task);
            if (save) save();
        }
    }

    // Удалить выполненные задачи
    public static void removeAllDone(boolean save){
        if(loaded){
            taskList.removeAllDone();
            if (save) save();
        }
    }

    // Инициировать обновление списка
    public static void update() {
        if (loaded) save();
    }

    // Получить список задач в виде массива
    static Task[] get() {
        if (loaded && updated) {
            return taskList.get();
        }
        return null;
    }

    // Сохранение JSON в файл
    private static void save() {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);

        FileOutputStream fos = null;

        try {
            fos = context.openFileOutput(FILENAME, Context.MODE_PRIVATE);
            fos.write(json.getBytes());
            if (adapter != null) {
                adapter.filter();
            }
            updated = true;
        } catch (IOException e) {
            updated = false;
        } finally {
            if (fos != null) {
                try {
                    fos.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static TaskAdapter createAdapter(Context context) {
        if (adapter != null)
            return adapter;
        return adapter = new TaskAdapter(Data.get(), context);
    }

    public static TaskAdapter getAdapter() {
        return adapter;
    }
}
