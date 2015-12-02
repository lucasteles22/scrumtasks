package com.example.lucasteles.scrumtasks;

import java.util.ArrayList;

public interface ITaskRepository {
    void insert(Task task);

    void update(Task task);

    void delete(Task task);

    ArrayList<Task> findAll();

    ArrayList<Task> findBySprint(long sprintId);

    ArrayList<Task> findByName(String name);

}
