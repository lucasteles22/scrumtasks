package com.example.lucasteles.scrumtasks;


import java.sql.Timestamp;

public class Task {
    private long id;

    private String name;

    private boolean finished;

    private Timestamp expectedTime;

    private Timestamp timeSpent;

    private long sprint_id;

    public void setId(long id) { this.id = id; }

    public long getId() { return this.id; }

    public String getName(){
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean getFinished(){
        return this.finished;
    }

    public void setFinished(boolean finished){
        this.finished = finished;
    }

    public void setSprintId(long sprint_id){
        this.sprint_id = sprint_id;
    }

    public long getSprintId(){
        return this.sprint_id;
    }

    public void setExpectedTime(Timestamp expectedTime) {
        this.expectedTime = expectedTime;
    }

    public Timestamp getExpectedTime() {
        return this.expectedTime;
    }

    public void setTimeSpent(Timestamp timeSpent) {
        this.timeSpent = timeSpent;
    }

    public Timestamp getTimeSpent() {
        return this.timeSpent;
    }
}
