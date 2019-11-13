package com.example.roundnetstattracker.model;


import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "teams")
public class Team {

    @PrimaryKey
    @NonNull
    public String uid = UUID.randomUUID().toString();

    public String name;

    public String player1Id;

    public String player2Id;

    @Ignore
    public Player player1;

    @Ignore
    public Player player2;

    public Team(String name){this.name = name; }

}
