package com.example.lucasteles.scrumtasks;

public class Sprint {
    private long id;

    private String name;

    private int position;

    private long project_id;

    public long getId(){
        return this.id;
    }

    public void setId(long id){
        this.id = id;
    }

    public String getName(){
        return this.name;
    }

    public void setName(String name){
        this.name = name;
    }

    public int getPosition(){
        return this.position;
    }

    public void setPosition(int position){
        this.position = position;
    }

    public long getProjectId(){
        return this.project_id;
    }

    public void setProjectId(long project_id){
        this.project_id = project_id;
    }
}