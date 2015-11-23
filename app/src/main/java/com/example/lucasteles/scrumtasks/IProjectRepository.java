package com.example.lucasteles.scrumtasks;

import java.util.ArrayList;

public interface IProjectRepository {
    void insert(Project project);

    void update(Project project);

    void delete(Project project);

    ArrayList<Project> findAll();

    ArrayList<Project> findByName(String name);
}
