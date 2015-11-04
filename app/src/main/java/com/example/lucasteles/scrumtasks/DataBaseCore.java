package com.example.lucasteles.scrumtasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DataBaseCore  extends SQLiteOpenHelper{
    private static final String DATABASE_NAME = "ScrumTasksDataBase";
    private static final int DATABASE_VERSION = 1;

    public DataBaseCore(Context ctx){
        super(ctx, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table projects(_id integer primary key autoincrement, name text not null);");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table projects;");
        onCreate(db);
    }
}
