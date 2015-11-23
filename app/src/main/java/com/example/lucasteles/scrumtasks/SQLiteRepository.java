package com.example.lucasteles.scrumtasks;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;

public class SQLiteRepository implements ISQLiteRepository {
    private SQLiteDatabase db;

    public SQLiteRepository(Context ctx){
        DataBaseCore dataBaseCore = new DataBaseCore(ctx);
        this.db = dataBaseCore.getWritableDatabase();
    }
    public IProjectRepository projectRepository() {
        return new ProjectRepository(db);
    }

    public ISprintRepository sprintRepository() { return  new SprintRepository(db); }
}