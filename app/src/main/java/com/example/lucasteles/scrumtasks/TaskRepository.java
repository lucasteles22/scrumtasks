package com.example.lucasteles.scrumtasks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;


import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

public class TaskRepository implements ITaskRepository{
    private SQLiteDatabase db;
    final SimpleDateFormat parser = new SimpleDateFormat("yyyy-MM-dd'T'hh:mm:ss");

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
                t.setExpectedTime(convertStringToTimestamp(cursor.getString(3)));
                t.setTimeSpent(convertStringToTimestamp(cursor.getString(4)));
                t.setSprintId(cursor.getLong(5));

                tasks.add(t);
            }while (cursor.moveToNext());
        }

        return tasks;
    }

    @Override
    public Task findById(Long id){
        Task task = new Task();
        String[] cols = new String[]{"_id", "name", "finished", "expectedTime", "timeSpent", "sprint_id" };
        Cursor cursor = db.query("tasks", cols, "_id = ?", new String[] { String.valueOf(id) }, null, null, "name ASC");

        if(cursor.getCount() > 0) {
            if(cursor.moveToFirst()) {
                task.setId(cursor.getLong(0));
                task.setName(cursor.getString(1));
                task.setFinished(cursor.getInt(2) > 0);
                task.setExpectedTime(convertStringToTimestamp(cursor.getString(3)));
                task.setTimeSpent(convertStringToTimestamp(cursor.getString(4)));
                task.setSprintId(cursor.getLong(5));
            }
        }

        return task;
    }

    private Timestamp convertStringToTimestamp(String str) {
        try {
            Date date = parser.parse(str);
            return new Timestamp(date.getTime());
        } catch (Exception e) {
            return null;
        }
    }
}