package com.example.lucasteles.scrumtasks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class SprintRepository implements ISprintRepository{
    private SQLiteDatabase db;

    public SprintRepository(SQLiteDatabase db){
        this.db = db;
    }

    @Override
    public void insert(Sprint sprint) {
        ContentValues values = new ContentValues();
        values.put("name", sprint.getName());
        values.put("position", sprint.getPosition());
        values.put("project_id", sprint.getProjectId());

        db.insert("sprints", null, values);
    }

    @Override
    public void update(Sprint sprint) {
        ContentValues values = new ContentValues();
        values.put("name", sprint.getName());
        values.put("position", sprint.getPosition());
        values.put("project_id", sprint.getProjectId());

        db.update("sprints", values, "_id = ?", new String[]{"" + sprint.getId()});
    }

    @Override
    public void delete(Sprint sprint) {
        db.delete("projects", "_id = ?", new String[]{"" + sprint.getId()});
    }

    @Override
    public ArrayList<Sprint> findByProject(long projectId) {
        ArrayList<Sprint> sprints = new ArrayList<Sprint>();
        String[] cols = new String[]{"_id", "name", "position", "project_id" };
        Cursor cursor = db.query("sprints", cols, "project_id = ?", new String[] { String.valueOf(projectId) }, null, null, "position ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Sprint s = new Sprint();
                s.setId(cursor.getLong(0));
                s.setName(cursor.getString(1));
                s.setPosition(cursor.getInt(2));
                s.setProjectId(cursor.getLong(3));

                sprints.add(s);
            } while (cursor.moveToNext());
        }

        return sprints;
    }

    @Override
    public Sprint findById(long id) {
        Sprint sprint = new Sprint();
        String[] cols = new String[]{"_id", "name", "position", "project_id" };
        Cursor cursor = db.query("sprints", cols, "_id = ?", new String[] { String.valueOf(id) }, null, null, null);

        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()) {
                sprint.setId(cursor.getLong(0));
                sprint.setName(cursor.getString(1));
                sprint.setPosition(cursor.getInt(2));
                sprint.setProjectId(cursor.getLong(3));
            }
        }

        return sprint;
    }
}
