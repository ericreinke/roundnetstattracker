package com.example.roundnetstattracker.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "teams")
public class Team {

    @PrimaryKey
    @NonNull
    public String uid = UUID.randomUUID().toString();
    public String name;

    public Team(String name){this.name = name; }

}
