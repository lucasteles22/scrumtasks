package com.example.lucasteles.scrumtasks;

public interface ISQLiteRepository {
    IProjectRepository projectRepository();

    ISprintRepository sprintRepository();

    ITaskRepository taskRepository();

    void close();
}
