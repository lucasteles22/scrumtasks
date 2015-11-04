package com.example.lucasteles.scrumtasks;


public class Task {
    private long id;

    private String name;

    private boolean finished;


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
}
