package com.example.lucasteles.scrumtasks;

import java.util.ArrayList;

public interface ISprintRepository {
    void insert(Sprint sprint);

    void update(Sprint sprint);

    void delete(Sprint sprint);

    ArrayList<Sprint> findByProject(long projectId);

    Sprint findById(long id);
}
