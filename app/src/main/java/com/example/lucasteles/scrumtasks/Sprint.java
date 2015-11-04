package com.example.lucasteles.scrumtasks;

public class Sprint {
    private long id;

    private String name;

    private int position;

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
}
