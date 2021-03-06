package com.example.lucasteles.scrumtasks;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;

public class ProjectRepository implements IProjectRepository{
    private SQLiteDatabase db;

    public ProjectRepository(SQLiteDatabase db){
        this.db = db;
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

    public Project findById(long projectId){
        Project project = new Project();
        String[] cols = new String[]{"_id", "name" };
        Cursor cursor = db.query("projects", cols, "_id = ?", new String[] { String.valueOf(projectId) }, null, null, null);

        if(cursor.getCount() > 0){
            if(cursor.moveToFirst()) {
                project.setId(cursor.getLong(0));
                project.setName(cursor.getString(1));
            }
        }

        return project;
    }
}

