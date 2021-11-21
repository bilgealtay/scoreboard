package com.ravensoftware.scoreboard.model.entity;

/**
 * Created by bilga on 20-11-2021
 */
public enum Status {

    ACTIVE("Active"),
    PASSIVE("Passive");

    private String name;

    Status(String name){
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
