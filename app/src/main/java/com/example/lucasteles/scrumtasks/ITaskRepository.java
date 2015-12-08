package com.example.lucasteles.scrumtasks;

import java.util.ArrayList;

public interface ITaskRepository {
    void insert(Task task);

    void update(Task task);

    void delete(Task task);

    ArrayList<Task> findBySprint(long sprintId);

    Task findById(Long id);

}
