package com.example.lucasteles.scrumtasks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

public class TaskRepository implements ITaskRepository{
    private SQLiteDatabase db;
    final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss.SSS");

    public TaskRepository(SQLiteDatabase db){
        this.db = db;
    }

    @Override
    public void insert(Task task) {
        ContentValues values = new ContentValues();
        values.put("name", task.getName());
        values.put("finished", task.getFinished());
        values.put("expectedTime", parser.format(task.getExpectedTime()));
        values.put("timeSpent", parser.format(task.getTimeSpent()));
        values.put("sprint_id", task.getSprintId());

        db.insert("tasks", null, values);
    }

    @Override
    public void update(Task task) {
        ContentValues values = new ContentValues();
        values.put("name", task.getName());
        values.put("finished", task.getFinished());
        values.put("expectedTime", parser.format(task.getExpectedTime()));
        values.put("timeSpent", parser.format(task.getTimeSpent()));
        values.put("sprint_id", task.getSprintId());

        db.update("tasks", values, "_id = ?", new String[]{"" + task.getId()});
    }

    @Override
    public void delete(Task task) {
        db.delete("tasks", "_id = ?", new String[]{"" + task.getId()});
    }

    @Override
    public ArrayList<Task> findAll() {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String[] cols = new String[]{"_id", "name", "finished", "expectedTime", "timeSpent", "sprint_id" };
        Cursor cursor = db.query("tasks", cols, null, null, null, null, "name ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Task t = new Task();
                t.setId(cursor.getLong(0));
                t.setName(cursor.getString(1));
                t.setFinished(cursor.getInt(2) > 0);
                t.setExpectedTime(Timestamp.valueOf(cursor.getString(3)));
                t.setTimeSpent(Timestamp.valueOf(cursor.getString(4)));
                t.setSprintId(cursor.getLong(5));

                tasks.add(t);
            }while (cursor.moveToNext());
        }

        return tasks;
    }

    @Override
    public ArrayList<Task> findBySprint(long sprintId) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        String[] cols = new String[]{"_id", "name", "finished", "expectedTime", "timeSpent", "sprint_id" };
        Cursor cursor = db.query("tasks", cols, "sprint_id = ?", new String[] { String.valueOf(sprintId) }, null, null, "name ASC");

        if(cursor.getCount() > 0) {
            cursor.moveToFirst();
            do {
                Task t = new Task();
                t.setId(cursor.getLong(0));
                t.setName(cursor.getString(1));
                t.setFinished(cursor.getInt(2) > 0);
                t.setExpectedTime(Timestamp.valueOf(cursor.getString(3)));
                t.setTimeSpent(Timestamp.valueOf(cursor.getString(4)));
                t.setSprintId(cursor.getLong(5));

                tasks.add(t);
            }while (cursor.moveToNext());
        }

        return tasks;
    }

    @Override
    public ArrayList<Task> findByName(String name) {
        ArrayList<Task> tasks = new ArrayList<Task>();
        return tasks;
    }
}