package com.example.lucasteles.scrumtasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteException;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseCore  extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "ScrumTasksDataBase";
    private static final int DATABASE_VERSION = 11;

    public DataBaseCore(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        createProjectsTable(db);
        createSprintsTable(db);
        createTasksTable(db);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        try {
            dropTasksTable(db);
            dropSprintsTable(db);
            dropProjectsTable(db);
            onCreate(db);
        } catch (SQLiteException e){

        }
    }

    //Projects
    private void createProjectsTable(SQLiteDatabase db){
        db.execSQL("create table projects(_id integer primary key autoincrement, name text not null);");
    }

    private void dropProjectsTable(SQLiteDatabase db){
        db.execSQL("drop table if exists projects;");
    }

    //Sprints
    private void createSprintsTable(SQLiteDatabase db){
        db.execSQL("create table sprints(" +
                "_id integer primary key autoincrement, " +
                "name text not null, " +
                "position integer not null, " +
                "project_id integer not null, " +
                "FOREIGN KEY(project_id) REFERENCES projects(_id));");
    }

    private void dropSprintsTable(SQLiteDatabase db){
        db.execSQL("drop table if exists sprints;");
    }

    //Tasks
    private void createTasksTable(SQLiteDatabase db){
        db.execSQL("create table tasks(" +
                "_id integer primary key autoincrement, " +
                "name text not null, " +
                "finished integer not null default 0," +
                "sprint_id integer not null, " +
                "timeSpent timestamp not null default current_timestamp, " +
                "expectedTime timestamp not null default current_timestamp, " +
                "FOREIGN KEY(sprint_id) REFERENCES sprints(_id));");
    }

    private void dropTasksTable(SQLiteDatabase db){
        db.execSQL("drop table if exists tasks");
    }
}
