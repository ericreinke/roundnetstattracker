package com.example.roundnetstattracker.model;


import androidx.annotation.NonNull;
import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;

import java.util.UUID;

@Entity(tableName = "teams")
public class Team {

    @PrimaryKey
    @NonNull
    public String uid;

    public String name;

    public String player1Id;

    public String player2Id;

    public Team(String uid, String name, String player1Id, String player2Id){
        this.uid = uid;
        this.name = name;
        this.player1Id = player1Id;
        this.player2Id = player2Id;
    }

    public Team(String name, String p1Id, String p2Id){
        this.name = name;
        this.player1Id = p1Id;
        this.player2Id = p2Id;
    }

    public Team(boolean anything){
        name = "team" + uid;
//        player1Id = player1.uid;
//        player2Id = player2.uid;
    }

}
