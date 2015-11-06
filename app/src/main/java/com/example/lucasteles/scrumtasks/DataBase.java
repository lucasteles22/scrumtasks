package com.example.lucasteles.scrumtasks;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import java.util.ArrayList;

public class DataBase {
    private SQLiteDatabase db;

    public DataBase(Context ctx){
        DataBaseCore dataBaseCore = new DataBaseCore(ctx);
        db = dataBaseCore.getWritableDatabase();
    }

    public void insert(Project project){
        ContentValues values = new ContentValues();
        values.put("name", project.getName());

        db.insert("projects", null, values);
    }

    public void update(Project project){
        ContentValues values = new ContentValues();
        values.put("name", project.getName());

        db.update("projects", values, "_id = ?", new String[]{"" + project.getId()});
    }

    public void delete(Project project){
        db.delete("projects", "_id = ?", new String[]{"" + project.getId()});
    }

    public ArrayList<Project> findAll(){
        ArrayList<Project> projects = new ArrayList<Project>();
        String[] cols = new String[]{"_id", "name"};
        Cursor cursor = db.query("projects", cols, null, null, null, null, "name ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Project p = new Project();
                p.setId(cursor.getLong(0));
                p.setName(cursor.getString(1));

                projects.add(p);
            }while (cursor.moveToNext());
        }

        return projects;
    }

    public ArrayList<Project> findByName(String name){
        ArrayList<Project> projects = new ArrayList<Project>();
        String[] cols = new String[]{"_id", "name"};
        Cursor cursor = db.query("projects", cols, "name like ?", new String[]{name}, null, null, "name ASC");

        if(cursor.getCount() > 0){
            cursor.moveToFirst();
            do {
                Project p = new Project();
                p.setId(cursor.getLong(0));
                p.setName(cursor.getString(1));

                projects.add(p);
            }while (cursor.moveToNext());
        }

        return projects;
    }
}
