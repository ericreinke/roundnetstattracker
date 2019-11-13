package com.example.roundnetstattracker.model;

import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "players")
public class Player {

    @PrimaryKey
    @NonNull
    public String uid = UUID.randomUUID().toString();

    private String name;

    public Player(String name){ this.name = name; }

    public void setName(String name){
        this.name = name;
    }

    public String getName(){
        return this.name;
    }
}
