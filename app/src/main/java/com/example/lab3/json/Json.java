package com.example.lab3.json;

import android.content.Context;

import com.example.lab3.task.Task;
import com.google.gson.Gson;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;

public class Json {

    /**
     * Контекст, содержащий данные файла
     */
    private static Context ctx;

    /**
     * Имя приватного файла в контексте.
     */
    private static final String FILE_NAME = "data2.json";

    /**
     * Список задач.
     */
    private static TaskList taskList = new TaskList();

    /**
     * Обновление происходит при вызове метода update, где переменной присваивается true.
     * Переменной присваивается false при обновлении списка задач.
     */
    private static boolean updated = true;

    /**
     * Установлено в true, если список был инициализирован из JSON.
     */
    private static boolean loaded = false;

    /**
     * Инициализация данных: загрузка данных из JSON.
     * Вызывается при запуске приложения.
     * @param context
     * @return
     */
    public static boolean init(Context context) {
        if (!loaded) {
            ctx = context;
            return loaded = load();
        }
        return false;
    }

    /**
     * Добавляет новую задачу в список задач.
     * @param task новая задача
     * @return успешность операции
     */
    public static boolean put(Task task) {
        if (loaded) {
            boolean success = taskList.add(task);
            updated = !success && updated;
            return success;
        }
        return false;
    }

    /**
     * Удаляет задачу из списка задач.
     * @param task задача
     * @return успешность выполнения
     */
    public static boolean remove(Task task) {
        if (loaded) {
            boolean success = taskList.remove(task);
            updated = !success && updated;
            return success;
        }
        return false;
    }

    public static boolean removeIfDone(){
        if(loaded){
            boolean success = taskList.removeIfDone();
            updated = !success && updated;
            return success;
        }
        return false;
    }

    public static boolean sortByDate(){
        if(loaded){
            boolean success = taskList.sortByDate();
            updated = !success && updated;
            return success;
        }
        return false;
    }

    /**
     * Сохранение данных в JSON.
     * @return успешность выполнения
     */
    public static boolean update() {
        if (loaded && !updated) {
            return updated = save();
        }
        return false;
    }

    /**
     * Возвращает массив задач.
     * @return массив задач; null, если данные не проинициализированы или не обновлены
     */
    public static Task[] get() {
        if (loaded && updated) {
            return taskList.get();
        }
        return null;
    }

    /**
     * Сохранение данных в JSON (без проверки обновления списка задач).
     * @return успешность выполнения
     */
    private static boolean save() {
        Gson gson = new Gson();
        String json = gson.toJson(taskList);

        FileOutputStream fos = null;

        try {
            fos = ctx.openFileOutput(FILE_NAME, Context.MODE_PRIVATE);
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

    /**
     * Загрузка данных из JSON.
     * @return успешность выполнения
     */
    private static boolean load() {
        InputStreamReader isr = null;
        FileInputStream fis = null;

        try {
            fis = ctx.openFileInput(FILE_NAME);
            isr = new InputStreamReader(fis);
            taskList = new Gson().fromJson(isr, TaskList.class);
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

        return taskList != null;
    }

    public static boolean isUpdated() {
        return updated;
    }

    public static boolean isLoaded() {
        return loaded;
    }
}
